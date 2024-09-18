package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private StudentRepository studentRepository;

  @GetMapping
  public ResponseEntity<List<Course>> get() {
    return ResponseEntity.ok(courseRepository.findAll());
  }

  @GetMapping("{code}")
  public ResponseEntity<Course> get(@PathVariable String code) {
    return ResponseEntity.ok(this.courseRepository.findById(code)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Course> post(@RequestBody Course course) {
    course.getStudents()
        .forEach(student -> this.studentRepository.save(student));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.courseRepository.save(course));
  }

  @PutMapping("{code}")
  public ResponseEntity<Course> put(@PathVariable String code, @RequestBody Course course) {
    return this.courseRepository.findById(code).map(existing -> {
      existing.update(course);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.courseRepository.save(existing));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{code}")
  public ResponseEntity<Course> delete(@PathVariable String code) {
    var existing = this.courseRepository.findById(code);
    if (existing.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    this.courseRepository.deleteById(code);
    return ResponseEntity.ok(existing.get());
  }
}