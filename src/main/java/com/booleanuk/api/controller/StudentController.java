package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentByID(@PathVariable int id) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found by ID"));
        return ResponseEntity.ok(student);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentRepository.save(student));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found by ID"));

        studentToUpdate.setFirst_name(student.getFirst_name());
        studentToUpdate.setLast_name(student.getLast_name());
        studentToUpdate.setBirth(student.getBirth());
        studentToUpdate.setCourse_title(student.getCourse_title());
        studentToUpdate.setCourse_start(student.getCourse_start());
        studentToUpdate.setAverage_grade(student.getAverage_grade());

        return new ResponseEntity<Student>(studentRepository.save(studentToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found by ID"));

        this.studentRepository.delete(studentToDelete);

        return ResponseEntity.ok(studentToDelete);
    }

}

