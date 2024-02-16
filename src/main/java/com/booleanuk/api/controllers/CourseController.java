package com.booleanuk.api.controllers;

import com.booleanuk.api.HelperUtils;
import com.booleanuk.api.models.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllCourses() {
        return HelperUtils.okRequest(this.courseRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCourse(@RequestBody Course course) {
        return HelperUtils.createdRequest(this.courseRepository.save(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteCourse(@PathVariable int id) {
        Optional<Course> courseToDelete = this.courseRepository.findById(id);
        if (courseToDelete.isPresent()) {
            this.courseRepository.deleteById(id);
            courseToDelete.get().setStudents(new ArrayList<>());
            return HelperUtils.okRequest(courseToDelete);
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("No course with that ID."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCourseById(@PathVariable int id, @RequestBody Course course) {
        Optional<Course> courseToUpdate = this.courseRepository.findById(id);
        courseToUpdate.ifPresent(value -> {
            value.setName(course.getName());
            value.setCourseStart(course.getCourseStart());
            value.setStudents(course.getStudents());
            this.courseRepository.save(value);
        });
        if (courseToUpdate.isPresent()) {
            return HelperUtils.createdRequest(courseToUpdate);
        } else {
           return HelperUtils.badRequest(new ApiResponse.Message("Could not update."));
        }
    }
}
