package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        CourseListResponse response = new CourseListResponse();
        response.set(this.courseRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
        Course course = this.courseRepository.findById(id).orElse(null);
        // 404 Not found if no course with that id
        if(course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Make response
        CourseResponse response = new CourseResponse();
        response.set(course);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        // 400 Bad request if not all fields are present
        if (course.getTitle() == null || course.getStartDate() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Make response
        CourseResponse response = new CourseResponse();
        response.set(this.courseRepository.save(course));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id,
                                                    @RequestBody Course course) {
        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);
        // 404 Not found if no course with that id
        if(courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // 400 Bad request if nothing to update
        if (course.getTitle() == null && course.getStartDate() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Update present fields
        if (course.getTitle() != null) {
            courseToUpdate.setTitle(course.getTitle());
        }
        if (course.getStartDate() != null) {
            courseToUpdate.setStartDate(course.getStartDate());
        }
        // Make response
        CourseResponse response = new CourseResponse();
        response.set(this.courseRepository.save(courseToUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id) {
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);
        // 404 Not found if no course with that id
        if(courseToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Delete course
        this.courseRepository.delete(courseToDelete);
        // Make response
        CourseResponse response = new CourseResponse();
        response.set(courseToDelete);
        return ResponseEntity.ok(response);
    }
}
