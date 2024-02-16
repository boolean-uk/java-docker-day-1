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


    @Autowired
    private StudentRepository studentRepository;
    @GetMapping
    public ResponseEntity<List<Student>>getAll(){
        return new ResponseEntity<>(this.studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getOne(@PathVariable int id){
        return new ResponseEntity<>(
                this.studentRepository
                        .findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Student> addOne(@Valid @RequestBody Student student){
        return new ResponseEntity<>(this.studentRepository.save(student),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateOne(@PathVariable int id, @Valid @RequestBody Student student){

        Student studentToUpdate = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());

        this.studentRepository.save(studentToUpdate);
        return new ResponseEntity<>(studentToUpdate, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteOne(@PathVariable int id){
        Student studentToDelete = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.studentRepository.delete(studentToDelete);
        return new ResponseEntity<>(studentToDelete, HttpStatus.OK);
    }
}
