package com.booleanuk.api;

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
    StudentRepository students;

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student){
        return new ResponseEntity<>(students.save(student), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        return ResponseEntity.ok(students.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student){
        Student toUpdate = students
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        toUpdate.setFirstName(student.getFirstName());
        toUpdate.setLastName(student.getLastName());
        toUpdate.setDob(student.getDob());
        toUpdate.setCourseTitle(student.getCourseTitle());
        toUpdate.setCourseStartDate(student.getCourseStartDate());
        students.save(toUpdate);

        return new ResponseEntity<>(toUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable int id){
        Student toDelete = students
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        students.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }
}