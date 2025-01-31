package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.CourseStudentGrade;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.CourseStudentGradeRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.responses.CourseListResponse;
import com.booleanuk.api.responses.CourseResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseStudentGradeRepository courseStudentGradeRepository;

    @GetMapping
    public ResponseEntity<CourseListResponse> getAllCourses(){
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Course request) {
        CourseResponse courseResponse = new CourseResponse();
        try{
            courseResponse.set(this.courseRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }



    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = null;

        try {
            courseToUpdate = this.courseRepository.findById(id).orElse(null);
            if(courseToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            courseToUpdate.setTitle(course.getTitle());
            courseToUpdate.setStartDate(course.getStartDate());
            courseToUpdate.setAverageGrade(course.getAverageGrade());
            courseToUpdate = this.courseRepository.save(courseToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToUpdate);

        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {
        Course courseToDelete = this.courseRepository.findById(id).orElse(null);

        if(courseToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(CourseStudentGrade courseStudentGrade : this.courseStudentGradeRepository.findAll()){
            if(courseStudentGrade.getCourse().equals(courseToDelete)){
                this.courseStudentGradeRepository.delete(courseStudentGrade);
            }
        }

        this.courseRepository.delete(courseToDelete);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseToDelete);

        return ResponseEntity.ok(courseResponse);
    }
}
