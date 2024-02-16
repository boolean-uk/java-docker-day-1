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
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        StudentListResponse response = new StudentListResponse();
        response.set(this.studentRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
        Student student = this.studentRepository.findById(id).orElse(null);
        // 404 Not found if no student with that id
        if(student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Student with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Make response
        StudentResponse response = new StudentResponse();
        response.set(student);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/course/{course_id}")
    public ResponseEntity<Response<?>> createStudent(@PathVariable int course_id,
                                                     @RequestBody Student student) {
        // 400 Bad request if not all fields are present
        if (student.getFirstName() == null || student.getLastName() == null ||
                student.getDateOfBirth() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Set course
        Course course = this.courseRepository.findById(course_id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        student.setCourse(course);
        // Make response
        StudentResponse response = new StudentResponse();
        response.set(this.studentRepository.save(student));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/course/{course_id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id,
                                                     @PathVariable int course_id,
                                                     @RequestBody Student student) {
        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);
        // 404 Not found if no student with that id
        if(studentToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Student with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // 404 Not found if course don't exist
        Course course = this.courseRepository.findById(course_id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentToUpdate.setCourse(course);
        // 400 Bad request if no fields are present
        if (student.getFirstName() == null && student.getLastName() == null &&
                student.getDateOfBirth() == null && student.getAverageGrade() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Update fields that are present
        if (student.getFirstName() != null) {
            studentToUpdate.setFirstName(student.getFirstName());
        }
        if(student.getLastName() != null) {
            studentToUpdate.setLastName(student.getLastName());
        }
        if (student.getDateOfBirth() != null) {
            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        }
        if (student.getAverageGrade() != null) {
            studentToUpdate.setAverageGrade(student.getAverageGrade());
        }
        // Make response
        StudentResponse response = new StudentResponse();
        response.set(this.studentRepository.save(studentToUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);
        // 404 Not found if no student with that id
        if(studentToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Student with that id not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Delete student
        this.studentRepository.delete(studentToDelete);
        // Make response
        StudentResponse response = new StudentResponse();
        response.set(studentToDelete);
        return ResponseEntity.ok(response);
    }

}
