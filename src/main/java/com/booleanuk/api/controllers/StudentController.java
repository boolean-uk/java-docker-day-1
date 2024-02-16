package com.booleanuk.api.controllers;

import com.booleanuk.api.HelperUtils;
import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllStudents() {
        return HelperUtils.okRequest(this.studentRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createStudent(@RequestBody Student student) {
        Optional<Course> optionalCourse = courseRepository.findById(student.getCourse().getId());

        if (optionalCourse.isPresent()) {
            student.setCourse(optionalCourse.get());
            return HelperUtils.createdRequest(this.studentRepository.save(student));
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("Course not found."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable int id) {
        Optional<Student> studentToDelete = this.studentRepository.findById(id);

        if (studentToDelete.isPresent()) {
            this.studentRepository.deleteById(id);
            return HelperUtils.okRequest(studentToDelete);
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("No student with that ID."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateStudentById(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> studentToUpdate = this.studentRepository.findById(id);

        studentToUpdate.ifPresent(value -> {
            value.setFirstName(student.getFirstName());
            value.setLastName(student.getLastName());
            value.setDateOfBirth(student.getDateOfBirth());
            value.setAverageGrade(student.getAverageGrade());
            value.setCourse(student.getCourse());
            this.studentRepository.save(value);
        });
        if (studentToUpdate.isPresent()) {
            return HelperUtils.createdRequest(studentToUpdate);
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("Could not update."));
        }
    }
}
