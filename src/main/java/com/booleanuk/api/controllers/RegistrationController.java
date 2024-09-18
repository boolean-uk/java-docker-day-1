package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Registration;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.RegistrationRepository;
import com.booleanuk.api.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("students/{studentId}/courses/{courseId}")
public class RegistrationController {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<Registration> create(@RequestBody Registration registration, @PathVariable int studentId, @PathVariable int courseId){
        Student student = this.studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with that id")
        );
        Course course = this.courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id")
        );
        registration.setStudent(student);
        registration.setCourse(course);
        return new ResponseEntity<>(this.registrationRepository.save(registration), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Registration> getAll(@PathVariable int studentId, @PathVariable int courseId){
        Student student = this.studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with that id")
        );
        Course course = this.courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id")
        );
        return this.registrationRepository.findAll();
    }
}
