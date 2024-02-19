package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Course;
import com.booleanuk.library.models.Student;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.CourseRepository;
import com.booleanuk.library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllBooks() {
        CourseListResponse listResponse = new CourseListResponse();
        listResponse.set(this.repository.findAll());
        return ResponseEntity.ok(listResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Course course = this.repository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CourseResponse response = new CourseResponse();
        response.set(course);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createBook(@RequestBody Course course ) {
        CourseResponse response = new CourseResponse();
        try {
            response.set(this.repository.save(course));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody Course request) {
        Course course = this.repository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        course.setTitle(request.getTitle());
        course.setStartDate(request.getStartDate());

        try {
            course = this.repository.save(course);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CourseResponse response = new CourseResponse();
        response.set(course);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteBook(@PathVariable int id) {
        Course course = this.repository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(course);
        CourseResponse response = new CourseResponse();
        response.set(course);
        return ResponseEntity.ok(response);
    }
}
