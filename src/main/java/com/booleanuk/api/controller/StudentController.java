package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        List<Student> allStudents = this.studentRepository.findAll();

        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(allStudents);

        return ResponseEntity.ok(studentListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        Student student = this.studentRepository.findById(id).orElse(null);

        if(student == null) {
            ErrorResponse errorResponse = this.createErrorResponse(id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);

        return ResponseEntity.ok(studentResponse);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if(!this.studentIsValid(student)) {
            ErrorResponse errorResponse = new ErrorResponse("Student could not be created, please check all required fields.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Student newStudent = this.studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(newStudent);

        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if(!this.studentIsValid(student)) {
            ErrorResponse errorResponse = new ErrorResponse("Student could not be updated, please check all required fields.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Student studentToBeUpdated = this.studentRepository.findById(id).orElse(null);

        if(studentToBeUpdated == null) {
            ErrorResponse errorResponse = this.createErrorResponse(id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        studentToBeUpdated.setFirstName(student.getFirstName());
        studentToBeUpdated.setLastName(student.getLastName());
        studentToBeUpdated.setDateOfBirth(student.getDateOfBirth());
        studentToBeUpdated.setCourseTitle(student.getCourseTitle());
        studentToBeUpdated.setStartDateForCourse(student.getStartDateForCourse());
        studentToBeUpdated.setAverageGrade(student.getAverageGrade());

        Student updatedStudent = this.studentRepository.save(studentToBeUpdated);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(updatedStudent);

        return new ResponseEntity<>(updatedStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        Student studentToBeDeleted = this.studentRepository.findById(id).orElse(null);

        if(studentToBeDeleted == null) {
            ErrorResponse errorResponse = this.createErrorResponse(id);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.studentRepository.deleteById(id);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToBeDeleted);

        return ResponseEntity.ok(studentResponse);
    }

    private boolean studentIsValid(Student student) {
        if(student.getFirstName() == null
                || student.getLastName() == null
                || student.getDateOfBirth() == null
                || student.getCourseTitle() == null
                || student.getStartDateForCourse() == null
                || student.getAverageGrade() == null) {

            return false;
        }
        return true;
    }

    private ErrorResponse createErrorResponse(int id) {
        return new ErrorResponse("Student with id " + id + " could not be found");
    }

}
