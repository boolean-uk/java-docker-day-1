package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.CustomResponse;
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
    public ResponseEntity<CustomResponse> getAllStudents() {
        CustomResponse customResponse = new CustomResponse("success", studentRepository.findAll());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getStudentById(@PathVariable int id) {
        CustomResponse customResponse = new CustomResponse("success", studentRepository.findById(id));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createStudent(@RequestBody Student student) {
        studentRepository.save(student);
        CustomResponse customResponse = new CustomResponse("success", studentRepository.findById(student.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null) {
            CustomResponse customResponse = new CustomResponse("error", "not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setAverageGrade(student.getAverageGrade());
        studentRepository.save(existingStudent);
        CustomResponse customResponse = new CustomResponse("success", studentRepository.findById(existingStudent.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteStudent(@PathVariable int id) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null) {
            CustomResponse customResponse = new CustomResponse("error", "not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }

        studentRepository.delete(existingStudent);
        CustomResponse customResponse = new CustomResponse("success", "deleted");
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }
}
