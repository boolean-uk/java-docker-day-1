package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("students")
public class StudentController {
  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @GetMapping
  public ResponseEntity<List<Student>> get() {
    return ResponseEntity.ok(studentRepository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Student> get(@PathVariable int id) {
    return ResponseEntity.ok(this.studentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  @PostMapping
  public ResponseEntity<Student> post(@RequestBody Student student) {
    Set<Course> managedCourses = new HashSet<>();
    for (Course course : student.getCourses()) {
      Course managedCourse = this.courseRepository.findById(course.getCode())
          .orElseGet(() -> this.courseRepository.save(course));
      managedCourses.add(managedCourse);

      if (managedCourse.getStudents() == null) {
        managedCourse.setStudents(new HashSet<>());
      }
      managedCourse.getStudents().add(student);
    }
    student.setCourses(managedCourses);

    Student savedStudent = this.studentRepository.save(student);

    this.courseRepository.saveAll(managedCourses);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
  }

  @PutMapping("{id}")
  public ResponseEntity<Student> put(@PathVariable int id, @RequestBody Student student) {
    return this.studentRepository.findById(id).map(existing -> {
      existing.update(student);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.studentRepository.save(existing));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Student> delete(@PathVariable int id) {
    var existing = this.studentRepository.findById(id);
    if (existing.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    this.studentRepository.deleteById(id);
    return ResponseEntity.ok(existing.get());
  }
}