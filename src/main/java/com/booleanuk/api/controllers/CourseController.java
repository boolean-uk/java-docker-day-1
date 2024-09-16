package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
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

    @GetMapping
    public ResponseEntity<Response<?>> getAllCourses(){
        Response<List<Course>> response = new Response<>();
        response.set(this.courseRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getACourse(@PathVariable int id){
        Course course = findTheCourse(id);
        if (course == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<Course> response = new Response<>();
        response.set(course);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course){
        if (course.getCourse_title() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Response<Course> response = new Response<>();
        response.set(this.courseRepository.save(course));
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course){
        Course updateCourse = findTheCourse(id);
        if (updateCourse == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (course.getCourse_title() == null ||
        course.getStart_date() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        updateCourse.setCourse_title(course.getCourse_title());
        updateCourse.setStart_date(course.getStart_date());

        Response<Course> response = new Response<>();
        response.set(this.courseRepository.save(updateCourse));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id){
        Course deleteCourse = findTheCourse(id);
        if (deleteCourse == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(deleteCourse);
        deleteCourse.setStudents(new ArrayList<>());
        Response<Course> response = new Response<>();
        response.set(deleteCourse);
        return ResponseEntity.ok(response);
    }


    private Course findTheCourse(int id ){
        return this.courseRepository.findById(id)
                .orElse(null);
    }
}
