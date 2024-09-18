package com.booleanuk.api.controller;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private static final String NOT_FOUND = "Course not found for ID: ";

    @Autowired
    public CourseController(CourseRepository repository, StudentRepository studentRepository) {
        this.courseRepository = repository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        List<Course> courseList = this.courseRepository.findAll();
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id)
                ));
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestParam int studentId, @Valid @RequestBody Course body) {
        Optional<Student> optionalStudent = this.studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        }

        Student student = optionalStudent.get();
        body.setStudent(student);
        Course createdCourse = this.courseRepository.save(body);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> update(@PathVariable int id, @Valid @RequestBody Course body) {
        Course course = this.courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id);
        }

        course.setTitle(body.getTitle());
        course.setDescription(body.getDescription());
        course.setCredits(body.getCredits());
        course.setStartDate(body.getStartDate());
        course.setEndDate(body.getEndDate());
        this.courseRepository.save(course);

        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> delete(@PathVariable int id) {
        Course course = this.courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id);
        }
        this.courseRepository.delete(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
