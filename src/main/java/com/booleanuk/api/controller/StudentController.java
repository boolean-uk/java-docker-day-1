package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.CourseStudentGrade;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.CourseStudentGradeRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.responses.*;
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

    @Autowired
    private CourseStudentGradeRepository courseStudentGradeRepository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents(){
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Student request) {
        StudentResponse studentResponse = new StudentResponse();
        try{
            studentResponse.set(this.studentRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }



    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = null;

        try {
            studentToUpdate = this.studentRepository.findById(id).orElse(null);
            if(studentToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            studentToUpdate = this.studentRepository.save(studentToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);

        if(studentToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(CourseStudentGrade courseStudentGrade : this.courseStudentGradeRepository.findAll()){
            if(courseStudentGrade.getStudent().equals(studentToDelete)){
                this.courseStudentGradeRepository.delete(courseStudentGrade);
            }
        }

        this.studentRepository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);

        return ResponseEntity.ok(studentResponse);
    }
}
