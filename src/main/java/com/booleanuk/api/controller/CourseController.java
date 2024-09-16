package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
@Autowired
private CourseRepository courseRepository;
    @GetMapping
    public List<Course> getAllCourses(){
        return this.courseRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") Integer id) {
        Course course = this.courseRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find course"));

        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return new ResponseEntity<>(this.courseRepository.save(course), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateACourse(@PathVariable int id,@RequestBody Course course){
        Course courseToUpdate = this.courseRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the course...."));

        courseToUpdate.setCourseTitle(course.getCourseTitle());
        courseToUpdate.setCourseStartDate(course.getCourseStartDate());

        return new ResponseEntity<>(this.courseRepository.save(courseToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteACourse(@PathVariable int id){
        Course courseToDelete = this.courseRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the course!!!"));
        this.courseRepository.delete(courseToDelete);
        return ResponseEntity.ok(courseToDelete);
    }
}