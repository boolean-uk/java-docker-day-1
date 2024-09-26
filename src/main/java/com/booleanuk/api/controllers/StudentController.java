package com.booleanuk.api.controllers;

import com.booleanuk.api.CustomResponse;
import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping
    public ResponseEntity<CustomResponse> getAllStudents() {
        if (studentRepository.count() < 1) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        return ResponseEntity.ok(new CustomResponse("success", this.studentRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getStudentByID(@PathVariable int id) {
        if (!studentRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Student student = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(new CustomResponse("Success", student));
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createStudent(@RequestBody Student student) {
        if (student == null) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        return ResponseEntity.ok(new CustomResponse("success", this.studentRepository.save(student)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteStudentByID(@PathVariable int id) {

        if (!studentRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Student student = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.studentRepository.delete(student);
        student.setCourses(new ArrayList<Course>());
        return ResponseEntity.ok(new CustomResponse("Success", student));


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateStudent(@PathVariable int id, @RequestBody Student student) {


        Student previousStu = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Student studentToUpdate = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        if (student.getFirstName() == null) {
            studentToUpdate.setFirstName(previousStu.getFirstName());
        }
        else {
            studentToUpdate.setFirstName(student.getFirstName());
        }
        if (student.getLastName() == null) {
            studentToUpdate.setLastName(previousStu.getLastName());
        }
        else {
            studentToUpdate.setLastName(student.getLastName());
        }
        if (student.getDob() == null) {
            studentToUpdate.setDob(previousStu.getDob());
        }
        else {
            studentToUpdate.setDob(student.getDob());
        }
        if (student.getStartDate() == null) {
            studentToUpdate.setStartDate(previousStu.getStartDate());
        }
        else {
            studentToUpdate.setStartDate(student.getStartDate());
        }
        if (student.getDegreeTitle() == null) {
            studentToUpdate.setDegreeTitle(previousStu.getDegreeTitle());
        }
        else {
            studentToUpdate.setDegreeTitle(student.getDegreeTitle());
        }
        if (student.getAverageGrade() == ' ') {
            studentToUpdate.setAverageGrade(previousStu.getAverageGrade());
        }
        else {
            studentToUpdate.setAverageGrade(student.getAverageGrade());
        }
        if (student.getCourses() == null) {
            studentToUpdate.setCourses(previousStu.getCourses());
        }
        else {
            studentToUpdate.setCourses(student.getCourses());
        }


        return ResponseEntity.ok(new CustomResponse("Success", this.studentRepository.save(studentToUpdate)));
    }
}


