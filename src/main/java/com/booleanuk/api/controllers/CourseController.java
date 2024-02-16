package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.StudentRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.CourseListResponse;
import com.booleanuk.api.responses.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @PostMapping("/{student_id}")
    public ResponseEntity<Response<?>> createCourse(@PathVariable int student_id, @RequestBody Course course) {
        CourseResponse courseResponse = new CourseResponse();
        if (!this.studentRepository.existsById(student_id)) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            course.setStudent(this.studentRepository.getReferenceById(student_id));
            courseResponse.set(this.courseRepository.save(course));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{student_id}")
    public ResponseEntity<Response<?>> getCoursesForAStudent(@PathVariable int student_id) {
        Student student = this.studentRepository.findById(student_id).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Course> allSpecifiedCourses = new ArrayList<>();
        for (Course course : this.courseRepository.findAll()) {
            if (course.getStudent().getId() == student_id) {
                allSpecifiedCourses.add(course);
            }
        }
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(allSpecifiedCourses);
        return ResponseEntity.ok(courseListResponse);
    }

    @PutMapping("/{student_id}/course/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int student_id, @PathVariable int id,
                                                    @RequestBody Course course) {
        Student student = this.studentRepository.findById(student_id).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Course courseToUpdate = this.courseRepository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setCredits(course.getCredits());
        courseToUpdate.setStartDate(course.getStartDate());
        courseToUpdate.setEndDate(course.getEndDate());
        courseToUpdate.setStudent(this.studentRepository.getReferenceById(student_id));
        try {
            courseToUpdate = this.courseRepository.save(courseToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{student_id}/course/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int student_id, @PathVariable int id) {
        Student student = this.studentRepository.findById(student_id).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);
        if (courseToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }
}
