package com.booleanuk.api.controller;


import com.booleanuk.api.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.exceptions.CustomParamaterConstraintException;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/courses")
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {

        Course course1 = new Course(course.getName(), course.getPoints());

        this.checkValidInput(course1);

        course1.setStudents(new HashSet<>());

        this.courseRepository.save(course1);

        return new ResponseEntity<>(new SuccessResponse(course1), HttpStatus.CREATED);

    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<>( this.courseRepository.findAll(), HttpStatus.CREATED);

    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Response<?>> getSpecificCourse(@PathVariable (name = "id") int id) {
        Course course = this.getACourse(id);

        return new ResponseEntity<>(new SuccessResponse(course), HttpStatus.CREATED);

    }


    @PutMapping("/courses/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable (name = "id") int id, @RequestBody Course course) {
        Course course1 = this.getACourse(id);

        course1.setName(course.getName());
        course1.setPoints(course.getPoints());
        if(course.getStudents() != null) {
            course1.setStudents(course.getStudents());
        } else {
            course1.setStudents(new HashSet<>());
        }


        this.checkValidInput(course1);
        this.courseRepository.delete(course1);

        return new ResponseEntity<>(new SuccessResponse(course1), HttpStatus.CREATED);

    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable (name = "id") int id) {
        Course course1 = this.getACourse(id);

        this.checkValidInput(course1);
        this.courseRepository.delete(course1);

        return new ResponseEntity<>(new SuccessResponse(course1), HttpStatus.CREATED);

    }

    @PostMapping("/students/{id}/courses")
    public ResponseEntity<Response<?>> assignCourseToStudent(@PathVariable (name = "id") int id, @RequestBody Course course) {

        Course course1 = this.courseRepository.findByName(course.getName()).orElseThrow();
        Student student = this.getAStudent(id);

        this.checkValidInput(course1);
        this.checkValidInput(student);

        course1.getStudents().add(student);
        student.getCourses().add(course1);

        return new ResponseEntity<>(new SuccessResponse(course1), HttpStatus.CREATED);
    }


    @DeleteMapping("/students/{id}/courses")
    public ResponseEntity<Response<?>> deleteCourseFromStudent(@PathVariable (name = "id") int id, @RequestBody String name) {
        Course course = this.courseRepository.findByName(name).orElseThrow();
        Student student = this.getAStudent(id);

        this.checkValidInput(course);
        this.checkValidInput(student);

        course.getStudents().remove(student);
        student.getCourses().remove(course);

        return new ResponseEntity<>(new SuccessResponse(course), HttpStatus.CREATED);
    }


    private Course getACourse(int id) {
        return this.courseRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No course with that ID found"));
    }


    private void checkValidInput(Course course) {
        if(course.getName() == null) {
            throw new CustomParamaterConstraintException("Bad request");
        }
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
