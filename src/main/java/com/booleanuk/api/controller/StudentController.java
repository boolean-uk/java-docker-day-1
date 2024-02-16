package com.booleanuk.api.controller;


import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return new ResponseEntity<>(this.studentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        this.checkValidInput(student);
        this.studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudent(@PathVariable (name = "id") int id) {
        Student student = this.getAStudent(id);
        return new ResponseEntity<>(new SuccessResponse(student), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable (name = "id") int id, @RequestBody Student student) {
        Student student1 = getAStudent(id);
        student1.setFirstName(student.getFirstName());
        student1.setAverageGrade(student.getAverageGrade());
        student1.setCourseTitle(student.getCourseTitle());
        student1.setLastName(student.getLastName());
        student1.setDateOfBrith(student.getDateOfBrith());
        student1.setStartDateOfCourse(student.getStartDateOfCourse());

        if(student.getCourses() == null) {
            student1.setCourses(new HashSet<>());
        } else {
            student1.setCourses(student.getCourses());
        }

        this.checkValidInput(student1);
        this.studentRepository.save(student1);

        return new ResponseEntity<>(student1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable (name = "id") int id) {
        Student student1 = getAStudent(id);

        this.studentRepository.delete(student1);
        return new ResponseEntity<>(student1, HttpStatus.OK);
    }

    private Student getAStudent(int id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No student with that ID found"));
    }


    private void checkValidInput(Student student) {
        if(student.getCourseTitle() == null
                || student.getFirstName() == null
                || student.getLastName() == null
                || student.getStartDateOfCourse() == null
                || student.getDateOfBrith() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
    }

}
