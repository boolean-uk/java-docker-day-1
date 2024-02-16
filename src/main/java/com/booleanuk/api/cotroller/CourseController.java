package com.booleanuk.api.cotroller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.payload.response.CourseListResponse;
import com.booleanuk.api.payload.response.CourseResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCourses() {
        CourseListResponse courseListResponse = new CourseListResponse();
        courseListResponse.set(this.courseRepository.findAll());
        return ResponseEntity.ok(courseListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Course course){
        Course courseCreate;
        try {
            courseCreate = this.courseRepository.save(course);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseCreate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Course course){
        Course courseUpdate = this.courseRepository.findById(id).orElse(null);
        if (courseUpdate == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found course");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } try {
            courseUpdate.setTitle(course.getTitle());
            courseUpdate.setStart(course.getStart());
            courseUpdate.setGrade(course.getGrade());
            this.courseRepository.save(courseUpdate);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(courseUpdate);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Course course = this.courseRepository.findById(id).orElse(null);
        if(course == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found course");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(course);
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.set(course);
        return ResponseEntity.ok(courseResponse);
    }
}
