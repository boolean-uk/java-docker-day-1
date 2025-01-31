package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getOne(@PathVariable int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable int id, @RequestBody Course course) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow();
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setStartDate(course.getStartDate());
        return courseRepository.save(courseToUpdate);
    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id) {
        Course c = courseRepository.findById(id).orElseThrow();
        courseRepository.deleteById(id);
        return c;
    }
}
