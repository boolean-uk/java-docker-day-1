package com.booleanuk.api.controller;

import com.booleanuk.api.dto.GradeDTO;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    CourseRepository repository;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping()
    public ResponseEntity<List<Course>> getAll(){
        List<Course> courses = this.repository.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getById(@PathVariable Integer id) {
        Course course = this.repository.findById(id).orElse(null);

        if(course == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        this.repository.save(course);

        return ResponseEntity.ok(course);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> update(@PathVariable Integer id, @RequestBody Course course){
        Course oldCourse = this.repository.findById(id).orElse(null);

        if(oldCourse != null) {
            oldCourse.setCourseName(course.getCourseName());
            oldCourse.setGrades(course.getGrades());
            oldCourse.setStartDate(course.getStartDate());

            this.repository.save(oldCourse);

            return ResponseEntity.ok(oldCourse);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> delete(@PathVariable Integer id) {
        Course deleteCourse = this.repository.findById(id).orElse(null);

        if(deleteCourse != null) {
            this.repository.delete(deleteCourse);
            return ResponseEntity.ok(deleteCourse);
        }

        return ResponseEntity.badRequest().body(null);
    }

}
