package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllCourses() {
        CustomResponse customResponse = new CustomResponse("success", courseRepository.findAll());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCourseById(@PathVariable int id) {
        CustomResponse customResponse = new CustomResponse("success", courseRepository.findById(id));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createCourse(@RequestBody Course course) {
        courseRepository.save(course);
        CustomResponse customResponse = new CustomResponse("success", courseRepository.findById(course.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse == null) {
            CustomResponse customResponse = new CustomResponse("error", "not found");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        }
        existingCourse.setTitle(course.getTitle());
        existingCourse.setCode(course.getCode());
        existingCourse.setStartDate(course.getStartDate());
        courseRepository.save(existingCourse);
        CustomResponse customResponse = new CustomResponse("success", courseRepository.findById(existingCourse.getId()));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Student> addStudentToCourse(@PathVariable int courseId, @PathVariable int studentId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        student.setCourse(course);
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
