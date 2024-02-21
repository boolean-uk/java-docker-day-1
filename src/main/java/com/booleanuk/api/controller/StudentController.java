package com.booleanuk.api.controller;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
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
        studentToUpdate.setDayOfBirth(student.getDayOfBirth());
        studentToUpdate.setCourseTitle(student.getCourseTitle());
        studentToUpdate.setStartDayOfCourse(student.getStartDayOfCourse());
        studentToUpdate.setAverageGrade(student.getAverageGrade());

        try {
            studentToUpdate = this.studentRepository.save(studentToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);
        if (studentToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }
}