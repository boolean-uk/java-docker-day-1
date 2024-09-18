package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.DTO.CourseRegistrationRequest;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegistrationController {

    @Autowired
    private CourseRepository courses;

    @Autowired
    private StudentRepository students;

    @PostMapping
    public ResponseEntity<Response<?>> register(@RequestBody CourseRegistrationRequest request) {
        Student student = this.students.findById(request.getStudentId().getId()).orElse(null);
        Course course = this.courses.findById(request.getStudentId().getId()).orElse(null);
        if (student == null || course == null) {
            ErrorResponse errorResponse = new ErrorResponse("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        student.addCourse(course);
        course.addStudent(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(this.students.save(student));

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

}
