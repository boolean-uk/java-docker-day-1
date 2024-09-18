package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Student;
import com.booleanuk.api.repository.StudentRepository;
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

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(this.studentRepository.save(student), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable int id) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + id + " not found."));
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        Student studentToUpdate = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + id + " not found."));

        studentToUpdate.setFirstName(studentDetails.getFirstName());
        studentToUpdate.setLastName(studentDetails.getLastName());
        studentToUpdate.setDateOfBirth(studentDetails.getDateOfBirth());
        studentToUpdate.setCourseTitle(studentDetails.getCourseTitle());
        studentToUpdate.setStartDate(studentDetails.getStartDate());
        studentToUpdate.setAverageGrade(studentDetails.getAverageGrade());

        return new ResponseEntity<>(this.studentRepository.save(studentToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + id + " not found."));
        this.studentRepository.delete(studentToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
