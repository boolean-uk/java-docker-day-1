package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.payload.request.StudentRequest;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.payload.response.StudentListResponse;
import com.booleanuk.api.payload.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    StudentRepository repository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody StudentRequest student) {
        StudentResponse studentResponse = new StudentResponse();

        Course course = this.courseRepository.findById(student.getCourseId()).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Student studentToAdd = new Student(student.getFirstName(), student.getLastName(), student.getDob());
        studentToAdd.setCourse(course);

        try {
            studentResponse.set(this.repository.save(studentToAdd));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Student studentToDelete = this.repository.findById(id).orElse(null);
        if (studentToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);

        return ResponseEntity.ok(studentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody StudentRequest student) {
        Student studentToUpdate = this.repository.findById(id).orElse(null);
        Course course = this.courseRepository.findById(student.getCourseId()).orElse(null);
        if (studentToUpdate == null || course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());
        studentToUpdate.setCourse(course);

        try {
            studentToUpdate = this.repository.save(studentToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }
}
