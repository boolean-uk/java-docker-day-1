package com.booleanuk.api.studentCourses;

import com.booleanuk.api.Courses.Course;
import com.booleanuk.api.Courses.CourseRepository;
import com.booleanuk.api.Responses.ErrorResponse;
import com.booleanuk.api.Responses.Response;
import com.booleanuk.api.Responses.StudentCourseResponse;
import com.booleanuk.api.Students.Student;
import com.booleanuk.api.Students.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("studentcourses")
public class StudentCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @PostMapping
    public ResponseEntity<Response<?>> createStudentCourse(@RequestBody StudentCourse studentCourse) {
        Student student = this.studentRepository.findById(studentCourse.getStudent().getId()).orElse(null);
        if (student == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Student not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentCourse.setStudent(student);
        Course course = this.courseRepository.findById(studentCourse.getCourse().getId()).orElse(null);
        if (course == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Course not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentCourse.setCourse(course);
        StudentCourseResponse studentCourseResponse = new StudentCourseResponse();
        try {
            studentCourseResponse.set(this.studentCourseRepository.save(studentCourse));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentCourseResponse, HttpStatus.CREATED);
    }
}
