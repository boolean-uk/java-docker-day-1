package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.CourseListResponse;
import com.booleanuk.api.response.CourseResponse;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private ErrorResponse errorResponse= new ErrorResponse();
    private CourseResponse courseResponse = new CourseResponse();
    private CourseListResponse courseListResponse = new CourseListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllCourses() {
        this.courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        if (course.getName() == null || course.getStartDate() == null || course.getAvgGrade() == null) {
            this.errorResponse.set("Could not create a new course, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.courseResponse.set(this.courseRepository.save(course));
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
        Course course = this.courseRepository.findById(id).orElse(null);
        if (course == null) {
            this.errorResponse.set("No course with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            this.errorResponse.set("No course with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (course.getName() == null || course.getStartDate() == null || course.getAvgGrade() == null) {
            this.errorResponse.set("Could not update the specified course, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        courseToUpdate.setName(course.getName());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setAvgGrade(course.getAvgGrade());
        this.courseResponse.set(this.courseRepository.save(courseToUpdate));
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCourseById(@PathVariable int id) {
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);
        if (courseToDelete == null) {
            this.errorResponse.set("No course with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(courseToDelete);
        this.courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }
}
