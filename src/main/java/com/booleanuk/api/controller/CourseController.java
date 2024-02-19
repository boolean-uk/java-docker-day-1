package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        Course createdCourse = this.courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        List<Course> courses = this.courseRepository.findAll();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) {
        Optional<Course> course = this.courseRepository.findById(id);
        if(course.isPresent()) {
            return ResponseEntity.ok().body(course.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable int id, @RequestBody Course course) {
        Optional<Course> courseToUpdate = this.courseRepository.findById(id);
        if(courseToUpdate.isPresent()) {
            Course updatedCourse = courseToUpdate.get();
            updatedCourse.setName(course.getName());
            updatedCourse.setStartDate(course.getStartDate());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable int id) {
        Optional<Course> courseToDelete = this.courseRepository.findById(id);
        if(courseToDelete.isPresent()) {
            this.courseRepository.delete(courseToDelete.get());
            return new ResponseEntity(courseToDelete, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}
