package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Course;
import com.booleanuk.api.library.repositories.CourseRepository;
import com.booleanuk.api.library.response.CourseListResponse;
import com.booleanuk.api.library.response.CourseResponse;
import com.booleanuk.api.library.response.ErrorResponse;
import com.booleanuk.api.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {

        Course course = this.courseRepository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        this.courseRepository.save(course);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {

        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        courseToUpdate.setName(course.getName());
        courseToUpdate.setStartDate(course.getStartDate());

        this.courseRepository.save(courseToUpdate);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {

        Course course = this.courseRepository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        this.courseRepository.delete(course);
        return ResponseEntity.ok(courseResponse);
    }
}
