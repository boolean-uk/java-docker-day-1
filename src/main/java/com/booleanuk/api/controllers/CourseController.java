package com.booleanuk.api.controllers;

import com.booleanuk.api.CustomResponse;
import com.booleanuk.api.models.Course;
import com.booleanuk.api.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        return ResponseEntity.ok(new CustomResponse("success", courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCourseById(@PathVariable int id) {
        if (!courseRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in the database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Course course = courseRepository.findById(id).orElse(null);
        return ResponseEntity.ok(new CustomResponse("Success", course));
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCourse(@RequestBody Course course) {
        if (course == null) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
        }
        return ResponseEntity.ok(new CustomResponse("success", courseRepository.save(course)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCourseById(@PathVariable int id) {
        if (!courseRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in the database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Course course = courseRepository.findById(id).orElse(null);
        courseRepository.delete(course);
        course.setStudents(new ArrayList<>());

        return ResponseEntity.ok(new CustomResponse("Success", course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        if (!courseRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in the database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse != null) {
            if (updatedCourse.getTotalPoints() != 0) {
                existingCourse.setTotalPoints(updatedCourse.getTotalPoints());
            }
            if (updatedCourse.getCourseTitle() != null) {
                existingCourse.setCourseTitle(updatedCourse.getCourseTitle());
            }
            if (updatedCourse.getCourseStart() != null) {
                existingCourse.setCourseStart(updatedCourse.getCourseStart());
            }

            return ResponseEntity.ok(new CustomResponse("Success", courseRepository.save(existingCourse)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("Error", new Error("Course not found")));
        }
    }
}
