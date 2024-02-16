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
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneStudent(@PathVariable int id)  {
        Course course = this.courseRepository.findById(id).orElse(null);
        if(course == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Course course)  {
        if(course.getTitle() == null || course.getStart() == null)  {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.courseRepository.save(course);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Course course)    {
        if(course.getTitle() == null || course.getStart() == null)  {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);
        if(courseToUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStart(course.getStart());
        this.courseRepository.save(courseToUpdate);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id)  {
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);
        if(courseToDelete == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.courseRepository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }
}
