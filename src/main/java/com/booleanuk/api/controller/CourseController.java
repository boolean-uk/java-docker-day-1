package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
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
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getACourse(@PathVariable int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        String nameRegex = "^[a-zA-Z\\s]*";
        if(course.getTitle().matches(nameRegex)) {
            Course newCourse = this.courseRepository.save(course);
            return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
       Course courseToUpdate = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

       if(!course.getTitle().isEmpty() && !course.getStartDate().isEmpty()) {
            courseToUpdate.setTitle(course.getTitle());
            courseToUpdate.setStartDate(course.getStartDate());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
        return new ResponseEntity<>(this.courseRepository.save(courseToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Course courseToDelete = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        if(courseToDelete.getStudents().isEmpty()) {
            this.courseRepository.delete(courseToDelete);
            return ResponseEntity.ok(courseToDelete);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course has students, and could not be deleted");
        }
    }
}
