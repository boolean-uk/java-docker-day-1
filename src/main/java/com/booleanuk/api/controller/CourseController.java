package com.booleanuk.api.controller;

import com.booleanuk.api.dto.CourseDTO;
import com.booleanuk.api.dto.CourseMapper;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseMapper courseMapper;

    @PostMapping
    CourseDTO addCourse(@Valid @RequestBody CourseDTO courseDTO, BindingResult result) throws ResponseStatusException {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more values missing. Please try again.");
        }

        Course savedCourse = this.courseRepository.save(courseMapper.toEntity(courseDTO));
        return this.courseMapper.toDTO(savedCourse);
    }

    @GetMapping
    List<CourseDTO> getAllCourses() {
        List<CourseDTO> courses = new ArrayList<>();
        this.courseRepository.findAll().forEach(course -> courses.add(this.courseMapper.toDTO(course)));
        return courses;
    }

    @GetMapping("/{id}")
    CourseDTO getCourseById(@PathVariable(name = "id") int id) throws ResponseStatusException {
        return this.courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with provided ID does not exist"));
    }

    @PutMapping("/{id}")
    CourseDTO updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable(name = "id") int id) throws ResponseStatusException {
        Course courseToUpdate = this.courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with provided ID does not exist."));

        update(courseToUpdate, courseDTO);

        Course savedCourse = this.courseRepository.save(courseToUpdate);

        return this.courseMapper.toDTO(savedCourse);
    }

    @Transactional
    @DeleteMapping("/{id}")
    CourseDTO deleteCourse(@PathVariable(name = "id") int id) throws ResponseStatusException {
        Course courseToDelete = this.courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with provided ID does not exist."));

        this.courseRepository.delete(courseToDelete);
        return this.courseMapper.toDTO(courseToDelete);
    }

    private void update(Course courseToUpdate, CourseDTO updatedCourse) {
        courseToUpdate.setTitle(updatedCourse.getTitle());
        courseToUpdate.setStartDate(updatedCourse.getStartDate());
        courseToUpdate.setAverageGrade(updatedCourse.getAverageGrade());
    }
}
