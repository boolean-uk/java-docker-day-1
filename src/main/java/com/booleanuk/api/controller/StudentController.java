package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.StudentListResponse;
import com.booleanuk.api.responses.StudentResponse;
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
    private StudentRepository students;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAll() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.students.findAll());
        return new ResponseEntity<>(studentListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Student toReturn = this.students.findById(id).orElse(null);
        if (toReturn == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(toReturn);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody Student toAdd) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(this.students.save(toAdd));
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Student toDelete = this.students.findById(id).orElse(null);

        if (toDelete == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.students.delete(toDelete);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(toDelete);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Student newData) {
        Student toUpdate = this.students.findById(id).orElse(null);

        if (toUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        toUpdate.setDob(newData.getDob());
        toUpdate.setAverageGrade(newData.getAverageGrade());
        toUpdate.setFirstName(newData.getFirstName());
        toUpdate.setLastName(newData.getLastName());

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(this.students.save(toUpdate));

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }
}
