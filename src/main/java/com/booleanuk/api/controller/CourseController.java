package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.payload.response.CourseListResponse;
import com.booleanuk.api.payload.response.CourseResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    CourseRepository repository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Course course) {
        CourseResponse courseResponse = new CourseResponse();
        try {
            courseResponse.set(this.repository.save(course));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        //createdCourse.setStudents(new ArrayList<>());

        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Course courseToDelete = this.repository.findById(id).orElse(null);
        if (courseToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        //Course.setStudents(new ArrayList<>());
        return ResponseEntity.ok(courseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = this.repository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setAvgGrade(course.getAvgGrade());

        try {
            courseToUpdate = this.repository.save(courseToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }
}
