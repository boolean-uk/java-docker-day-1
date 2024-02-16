package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return new ResponseEntity<>(this.courseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getOne(@PathVariable int id) {
        return new ResponseEntity<>(
                this.courseRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Course> addOne(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(this.courseRepository.save(course), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateOne(@PathVariable int id, @Valid @RequestBody Course course) {
        Course courseToUpdate = this.courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        courseToUpdate.setName(course.getName());
        courseToUpdate.setStartDate(course.getStartDate());

        this.courseRepository.save(courseToUpdate);
        return new ResponseEntity<>(courseToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteOne(@PathVariable int id) {
        Course courseToDelete = this.courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.courseRepository.delete(courseToDelete);
        return new ResponseEntity<>(courseToDelete, HttpStatus.OK);
    }
}