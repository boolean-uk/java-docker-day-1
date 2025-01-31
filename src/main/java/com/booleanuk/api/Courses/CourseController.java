package com.booleanuk.api.Courses;

import com.booleanuk.api.Responses.*;
import com.booleanuk.api.Students.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository repository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        CourseResponse courseResponse = new CourseResponse();
        try {
            courseResponse.set(this.repository.save(course));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
        Course course = this.repository.findById(id).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = this.repository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        courseToUpdate.setName(course.getName());
        courseToUpdate.setStartDateForCourse(course.getStartDateForCourse());
        courseToUpdate.setAverageGrade(courseToUpdate.getAverageGrade());

        try {
            courseToUpdate = this.repository.save(courseToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {
        Course courseToDelete = this.repository.findById(id).orElse(null);
        if (courseToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }

}
