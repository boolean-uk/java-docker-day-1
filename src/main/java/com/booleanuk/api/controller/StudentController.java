package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

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

    @PostMapping("/{courseID}")
    public ResponseEntity<Response<?>> createStudent(@PathVariable int courseID, @RequestBody Student student) {
        Course course = this.courseRepository.findById(courseID).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Student student1 = new Student();
        try {
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setBirthDate(student.getBirthDate());
            student1.setAvrageGrade(student.getAvrageGrade());
            student1.setCourse(course);
            this.studentRepository.save(student1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create student");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student1);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{courseID}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @PathVariable int courseID, @RequestBody Student student) {
        Student student1 = this.studentRepository.findById(id).orElse(null);
        if (student1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No student with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Course course = this.courseRepository.findById(courseID).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No course with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            student1.setFirstName(student.getFirstName());
            student1.setLastName(student.getLastName());
            student1.setBirthDate(student.getBirthDate());
            student1.setAvrageGrade(student.getAvrageGrade());
            student1.setCourse(course);
            this.studentRepository.save(student1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update student, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student1);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student student1 = this.studentRepository.findById(id).orElse(null);
        if (student1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No student with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(student1);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student1);
        return ResponseEntity.ok(studentResponse);
    }
}
