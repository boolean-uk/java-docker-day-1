package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.CourseListResponse;
import com.booleanuk.api.response.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

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
    public ResponseEntity<Response<?>> updateCourse(@RequestBody Course course) {
        Course course1;
        try {
            course1 = this.courseRepository.save(course);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create course");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course1);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course course1 = this.courseRepository.findById(id).orElse(null);
        if (course1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            course1.setTitle(course.getTitle());
            course1.setStartDate(course.getStartDate());
            this.courseRepository.save(course1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update course, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course1);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {
        Course course1 = this.courseRepository.findById(id).orElse(null);
        if (course1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(course1);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course1);
        return ResponseEntity.ok(courseResponse);
    }
}
