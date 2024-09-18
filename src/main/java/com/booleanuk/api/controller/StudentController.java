package com.booleanuk.api.controller;


import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllStudents(){
        List<Student> students = this.studentRepository.findAll();
        StudentListResponse response = new StudentListResponse();

        response.set(students);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student){

        if (student.getFirstName() == null || student.getLastName() == null ||
                student.getDateOfBirth() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new student, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


        StudentResponse response = new StudentResponse();

        this.studentRepository.save(student);
        response.set(student);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student){
        if (student.getFirstName() == null || student.getLastName() == null ||
                student.getDateOfBirth() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new student, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);

        if (studentToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setCourseTitle(student.getCourseTitle());
        studentToUpdate.setCourseStartDate(student.getCourseStartDate());
        studentToUpdate.setAvgGrade(student.getAvgGrade());

        StudentResponse response = new StudentResponse();

        this.studentRepository.save(studentToUpdate);
        response.set(studentToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id){

       Student studentToDelete = this.studentRepository.findById(id).orElse(null);
       if (studentToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No student with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

       StudentResponse response = new StudentResponse();

       this.studentRepository.delete(studentToDelete);
       response.set(studentToDelete);

        return ResponseEntity.ok(response);
    }
}
