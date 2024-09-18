package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private final CourseRepository repository;

    public CourseController(CourseRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    /*@GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }*/

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody Course course) {
        return new ResponseEntity<>(this.repository.save(course),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Integer id,
                                             @RequestBody Course course) {
        Course courseToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing course with that ID found")
        );
        courseToUpdate.setCourseTitle(course.getCourseTitle());
        courseToUpdate.setCourseStartDate(course.getCourseStartDate());
        courseToUpdate.setAvgGrade(course.getAvgGrade());
        return new ResponseEntity<>(this.repository.save(courseToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") Integer id) {
        Course courseToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No course with that ID were found")
        );
        this.repository.delete(courseToDelete);
        return ResponseEntity.ok(courseToDelete);
    }
}
