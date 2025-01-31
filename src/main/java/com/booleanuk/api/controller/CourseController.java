package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.courseRepository.save(course), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable int id) {
        return new ResponseEntity<>(this.courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No course with that id were found")), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the course, please check all required fields are correct");
        }
        Course updateCourse = this.courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No course with that id were found "));
        updateCourse.setCourseTitle(course.getCourseTitle());
        updateCourse.setStartDateForCourse(course.getStartDateForCourse());

        return new ResponseEntity<>(this.courseRepository.save(updateCourse), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Course deletedCourse = this.courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course with that id were found"));
        this.courseRepository.delete(deletedCourse);
        return ResponseEntity.ok(deletedCourse);
    }
}
