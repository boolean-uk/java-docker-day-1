package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
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

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getAStudent(@PathVariable int id) {
        Student author = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        String nameRegex = "^[a-zA-Z\\s]*";
        if(student.getFirstName().matches(nameRegex) &&
                student.getLastName().matches(nameRegex)) {
            Course course = courseRepository.findById(student.getCourse().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
            student.setCourse(course);
            Student newStudent = this.studentRepository.save(student);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Course course = courseRepository.findById(student.getCourse().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Student studentToUpdate = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        String nameRegex = "^[a-zA-Z\\s]*";
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        if(student.getFirstName().matches(nameRegex) &&
                student.getLastName().matches(nameRegex) &&
                student.getAverageGrade().matches("^[a-zA-Z0-9]$")) {
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate.setCourse(course);
            studentToUpdate.setAverageGrade(student.getAverageGrade());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
        return new ResponseEntity<>(this.studentRepository.save(studentToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Student studentToDelete = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.studentRepository.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }
}
