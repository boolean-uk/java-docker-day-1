package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentRepository studentRepository;
    private static final String NOT_FOUND = "Student not found for ID: ";

    @Autowired
    public StudentController(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> studentList = this.studentRepository.findAll();
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id)
        ));
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student body) {
        Student createdStudent = this.studentRepository.save(body);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @Valid @RequestBody Student body) {
        Student student = this.studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id);
        }

        student.setFirstName(body.getFirstName());
        student.setLastName(body.getLastName());
        student.setAverageGrade(body.getAverageGrade());
        student.setStartDate(body.getStartDate());
        student.setDateOfBirth(body.getDateOfBirth());
        this.studentRepository.save(student);

        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable int id) {
        Student student = this.studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND + id);
        }
        this.studentRepository.delete(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
