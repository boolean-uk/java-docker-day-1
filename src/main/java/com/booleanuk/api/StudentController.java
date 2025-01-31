
package com.booleanuk.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository repo;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student newStudent) {
        return new ResponseEntity<Student>(repo.save(newStudent), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Student studentToDelete = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        repo.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setCourseTitle(student.getCourseTitle());
        studentToUpdate.setCourseStartDate(student.getCourseStartDate());
        studentToUpdate.setAvgGrade(student.getAvgGrade());

        return new ResponseEntity<Student>(repo.save(studentToUpdate), HttpStatus.CREATED);
    }
}
