package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Student;
import com.booleanuk.api.library.repositories.StudentRepository;
import com.booleanuk.api.library.response.ErrorResponse;
import com.booleanuk.api.library.response.Response;
import com.booleanuk.api.library.response.StudentListResponse;
import com.booleanuk.api.library.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {

        Student student = this.studentRepository.findById(id).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        return ResponseEntity.ok(studentResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        this.studentRepository.save(student);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {

        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);
        if (studentToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());

        this.studentRepository.save(studentToUpdate);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {

        Student student = this.studentRepository.findById(id).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        this.studentRepository.delete(student);
        return ResponseEntity.ok(studentResponse);
    }
}