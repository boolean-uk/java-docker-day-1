package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.payload.response.CourseListResponse;
import com.booleanuk.api.payload.response.CourseResponse;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        CourseResponse courseResponse = new CourseResponse();
        try {
            courseResponse.set(this.courseRepository.save(course));
        } catch (Exception e) {
            return badRequest();
        }
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
        Course course = this.getACourse(id);
        if (course == null) {
            return notFound();
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = this.getACourse(id);
        if (courseToUpdate == null) {
            return notFound();
        }
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStartDate(course.getStartDate());

        try {
            courseToUpdate = this.courseRepository.save(courseToUpdate);
        } catch (Exception e) {
            badRequest();
        }

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {
        Course courseToDelete = this.getACourse(id);
        if (courseToDelete == null) {
            return notFound();
        }
        this.courseRepository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);
        return ResponseEntity.ok(courseResponse);
    }

    private Course getACourse(int id){
        return this.courseRepository.findById(id).orElse(null);
    }

    private ResponseEntity<Response<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create Course, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No Course with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
