package com.booleanuk.api.student;

import com.booleanuk.api.response.*;
import com.booleanuk.api.response.Error;
import com.booleanuk.api.student.Student;
import com.booleanuk.api.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {



    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new StudentListResponse(this.studentRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getStudent(@PathVariable int id) {
        Student student = this.studentRepository
                .findById(id)
                .orElse(null);
        if (student == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Student not found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new StudentResponse(student), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createStudent(@RequestBody Student student) {

        if(student.getFirstName().isEmpty() || student.getLastName().isEmpty() || student.getDateOfBirth().isEmpty() || student.getTitle().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        this.studentRepository.save(student);
        return new ResponseEntity<>(new StudentResponse(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteStudent (@PathVariable int id) {
        Student deleted = this.studentRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Course not found")), HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(deleted);
        return new ResponseEntity<>(new StudentResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateStudent (@PathVariable int id, @RequestBody Student student) {

        Student studentToUpdate = this.studentRepository
                .findById(id)
                .orElse(null);
        if(studentToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Student not found")), HttpStatus.NOT_FOUND);
        }

        if(student.getFirstName().isEmpty() || student.getLastName().isEmpty() || student.getDateOfBirth().isEmpty() || student.getTitle().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setTitle(student.getTitle());
        studentToUpdate.setStartDateCourse(student.getStartDateCourse());
        studentToUpdate.setAvgGrade(student.getAvgGrade());

        this.studentRepository.save(studentToUpdate);
        return new ResponseEntity<>(new StudentResponse(studentToUpdate), HttpStatus.OK);
    }
}
