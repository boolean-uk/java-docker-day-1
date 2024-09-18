package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.repositories.CourseRepository;
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

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course){
        return new ResponseEntity<>(this.courseRepository.save(course), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Course> getAll(){
        return this.courseRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getOne(@PathVariable int id){
        Course course = this.courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id")
        );
        return ResponseEntity.ok(course);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> update(@PathVariable int id, @RequestBody Course course){
        Course courseToUpdate = this.courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id")
        );
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStartDate(course.getStartDate());
        return new ResponseEntity<>(this.courseRepository.save(courseToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> delete(@PathVariable int id){
        Course course = this.courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id")
        );
        this.courseRepository.delete(course);
        return ResponseEntity.ok(course);
    }
}
