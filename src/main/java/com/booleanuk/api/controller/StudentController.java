package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student createdStudent = this.studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = this.studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        Optional<Student> student = this.studentRepository.findById(id);
        if(student.isPresent()) {
            return ResponseEntity.ok().body(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> studentToUpdate = this.studentRepository.findById(id);
        if(studentToUpdate.isPresent()) {
            Student updatedStudent = studentToUpdate.get();
            updatedStudent.setFirstName(student.getFirstName());
            updatedStudent.setLastName(student.getLastName());
            updatedStudent.setDateOfBirth(student.getDateOfBirth());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete(@PathVariable int id) {
        Optional<Student> studentToDelete = this.studentRepository.findById(id);
        if(studentToDelete.isPresent()) {
            this.studentRepository.delete(studentToDelete.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
