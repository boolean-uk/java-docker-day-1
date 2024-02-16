package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(courses);
        return ResponseEntity.ok(courseListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
        Course returnCourse = this.courseRepository.findById(id).orElse(null);
        if (returnCourse == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No courses matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(returnCourse);
        return ResponseEntity.ok(courseResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        if (course.getTitle() == null || course.getDescription() == null
                || course.getStartDate() == null  || course.getStudyPoints() < 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the course, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Course createdCourse = this.courseRepository.save(course);
        createdCourse.setStudents(new ArrayList<>());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(createdCourse);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUserById(@PathVariable int id, @RequestBody Course course) {
        if (course.getTitle() == null || course.getDescription() == null
                || course.getStartDate() == null  || course.getStudyPoints() < 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the course's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);

        if(courseToUpdate == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No courses matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setStudyPoints(course.getStudyPoints());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCourseById(@PathVariable int id) {
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);

        if(courseToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No courses matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.courseRepository.delete(courseToDelete);
        courseToDelete.setStudents(new ArrayList<Student>());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }
}
