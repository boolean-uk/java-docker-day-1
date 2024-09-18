package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.CourseListResponse;
import com.booleanuk.api.responses.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courses;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAll() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courses.findAll());
        return new ResponseEntity<>(courseListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Course toReturn = this.courses.findById(id).orElse(null);
        if (toReturn == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(toReturn);
        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@RequestBody Course toAdd) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(this.courses.save(toAdd));
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Course toDelete = this.courses.findById(id).orElse(null);

        if (toDelete == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.courses.delete(toDelete);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(toDelete);

        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Course newData) {
        Course toUpdate = this.courses.findById(id).orElse(null);

        if (toUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        toUpdate.setTitle(newData.getTitle());
        toUpdate.setStartDate(newData.getStartDate());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(this.courses.save(toUpdate));

        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }
}
