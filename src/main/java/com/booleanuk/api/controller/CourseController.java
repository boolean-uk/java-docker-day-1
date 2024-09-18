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

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCourses(){
        List<Course> courses = this.courseRepository.findAll();
        CourseListResponse response = new CourseListResponse();

        response.set(courses);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course){

        if (course.getName() == null || course.getStartDate() == null || course.getAvgGrade() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new course, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


        CourseResponse response = new CourseResponse();

        this.courseRepository.save(course);
        response.set(course);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course){
        if (course.getName() == null || course.getStartDate() == null || course.getAvgGrade() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new student, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);

        if (courseToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        courseToUpdate.setName(course.getName());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setAvgGrade(course.getAvgGrade());

        CourseResponse response = new CourseResponse();

        this.courseRepository.save(courseToUpdate);
        response.set(courseToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // update to add student into a class
    @PutMapping("{courseId}/students/{studId}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int courseId, @PathVariable int studId){

        Course course = this.courseRepository.findById(courseId).orElse(null);
        Student student = this.studentRepository.findById(studId).orElse(null);

        if (course == null || student == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No course or student with that id found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CourseResponse response = new CourseResponse();

        student.setCourse(course);
        course.getStudents().add(student);

        this.courseRepository.save(course);
        response.set(course);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id){

        Course courseToDelete = this.courseRepository.findById(id).orElse(null);
        if (courseToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CourseResponse response = new CourseResponse();

        this.courseRepository.delete(courseToDelete);
        response.set(courseToDelete);

        return ResponseEntity.ok(response);
    }

}
