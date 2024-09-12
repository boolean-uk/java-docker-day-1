package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
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
    public ResponseEntity<Response<?>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(courseRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getAllCourseStudents(@PathVariable int id) {
        Course course = findCourse(id);
        if(course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(course.getStudents()));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
        if(containsNull(course)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        Response<Course> response = new SuccessResponse<>(courseRepository.save(course));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteCourse(@PathVariable int id) {
        Course courseToDelete = findCourse(id);
        if(courseToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(!courseToDelete.getStudents().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("course has students attached to it"));
        }
        courseRepository.delete(courseToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(courseToDelete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = findCourse(id);
        if(courseToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(course.getTitle() != null) {
            courseToUpdate.setTitle(course.getTitle());
        }
        if(course.getStartDate() != null) {
            courseToUpdate.setStartDate(course.getStartDate());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(courseRepository.save(courseToUpdate)));
    }

    private Course findCourse(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Course course) {
        return course.getTitle() == null || course.getStartDate() == null;
    }
}

