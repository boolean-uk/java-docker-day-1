package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Student;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.payload.response.StudentListResponse;
import com.booleanuk.api.payload.response.StudentResponse;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        StudentResponse studentResponse = new StudentResponse();
        try {
            studentResponse.set(this.studentRepository.save(student));
        } catch (Exception e) {
            return badRequest();
        }
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
        Student student = this.getAStudent(id);
        if (student == null) {
            return notFound();
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        return ResponseEntity.ok(studentResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.getAStudent(id);
        if (studentToUpdate == null) {
            return notFound();
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setAverageGrade(student.getAverageGrade());

        try {
            studentToUpdate = this.studentRepository.save(studentToUpdate);
        } catch (Exception e) {
            badRequest();
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.getAStudent(id);
        if (studentToDelete == null) {
            return notFound();
        }
        this.studentRepository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }

    private Student getAStudent(int id){
        return this.studentRepository.findById(id).orElse(null);
    }

    private ResponseEntity<Response<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create Student, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No Student with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
