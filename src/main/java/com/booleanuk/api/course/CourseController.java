package com.booleanuk.api.course;

import com.booleanuk.api.response.CourseListResponse;
import com.booleanuk.api.response.CourseResponse;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Error;
import com.booleanuk.api.response.Response;
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
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new CourseListResponse(this.courseRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getCourse(@PathVariable int id) {
        Course course = this.courseRepository
                .findById(id)
                .orElse(null);
        if (course == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Course not found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CourseResponse(course), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createCourse(@RequestBody Course course) {

        if(course.getTitle().isEmpty() ){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        this.courseRepository.save(course);
        return new ResponseEntity<>(new CourseResponse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCourse (@PathVariable int id) {
        Course deleted = this.courseRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Course not found")), HttpStatus.NOT_FOUND);
        }
        this.courseRepository.delete(deleted);
        return new ResponseEntity<>(new CourseResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCourse (@PathVariable int id, @RequestBody Course course) {

        Course courseToUpdate = this.courseRepository
                .findById(id)
                .orElse(null);
        if(courseToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Course not found")), HttpStatus.NOT_FOUND);
        }

        if(course.getTitle().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStartDate(course.getStartDate());

        this.courseRepository.save(courseToUpdate);
        return new ResponseEntity<>(new CourseResponse(courseToUpdate), HttpStatus.OK);
    }
}
