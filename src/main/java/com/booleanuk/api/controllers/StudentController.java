package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student){
        return new ResponseEntity<>(this.studentRepository.save(student), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Student> getAll(){
        return this.studentRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getOne(@PathVariable int id){
        Student student = this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with that id")
        );
        return ResponseEntity.ok(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student){
        Student studentToUpdate = this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with that id")
        );
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());
        studentToUpdate.setAverageGrade(student.getAverageGrade());
        return new ResponseEntity<>(this.studentRepository.save(studentToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable int id){
        Student student = this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with that id")
        );
        this.studentRepository.delete(student);
        return ResponseEntity.ok(student);
    }
}
