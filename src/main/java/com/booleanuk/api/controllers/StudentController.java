package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a specific student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable UUID id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Create a new student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Update an existing student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable UUID id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(updatedStudent.getFirstName());
                    student.setLastName(updatedStudent.getLastName());
                    student.setDateOfBirth(updatedStudent.getDateOfBirth());
                    student.setCourse(updatedStudent.getCourse());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        if (studentRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        studentRepository.deleteById(id);
    }
}