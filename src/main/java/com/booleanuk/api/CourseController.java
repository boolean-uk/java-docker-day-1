package com.booleanuk.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    CourseRepository courses;

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course){
        course.setStudents(new ArrayList<>());

        return new ResponseEntity<>(courses.save(course), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll(){
        return ResponseEntity.ok(courses.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> update(@PathVariable int id, @RequestBody Course course){
        Course toUpdate = courses
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        toUpdate.setTitle(course.getTitle());
        toUpdate.setStartDate(course.getStartDate());
        courses.save(toUpdate);

        return new ResponseEntity<>(toUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> delete(@PathVariable int id){
        Course toDelete = courses
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        courses.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }
}
