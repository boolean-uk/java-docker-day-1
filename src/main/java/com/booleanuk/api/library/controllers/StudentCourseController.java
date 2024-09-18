package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Course;
import com.booleanuk.api.library.models.StudentCourse;
import com.booleanuk.api.library.models.Student;
import com.booleanuk.api.library.repositories.CourseRepository;
import com.booleanuk.api.library.repositories.StudentCourseRepository;
import com.booleanuk.api.library.repositories.StudentRepository;
import com.booleanuk.api.library.response.StudentCourseListResponse;
import com.booleanuk.api.library.response.StudentCourseResponse;
import com.booleanuk.api.library.response.ErrorResponse;
import com.booleanuk.api.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students/{studentId}/courses/{courseId}")
public class StudentCourseController {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getStudentCoursesByStudentAndCourse(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
        
        Course course = this.courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Student student = this.studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<StudentCourse> studentCourses = this.studentCourseRepository.findByCourseAndStudent(course, student);
        if (!studentCourses.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Student has never been enrolled in that course");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        StudentCourseListResponse studentCourseListResponse = new StudentCourseListResponse();
        studentCourseListResponse.set(studentCourses);
        return ResponseEntity.ok(studentCourseListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudentCourse(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId, @RequestBody StudentCourse studentCourse) {

        Course course = this.courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Student student = this.studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }


        StudentCourseResponse studentCourseResponse = new StudentCourseResponse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        this.studentCourseRepository.save(studentCourse);
        studentCourseResponse.set(studentCourse);
        return new ResponseEntity<>(studentCourseResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> giveGrade(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId, @PathVariable("id") int id, @RequestBody StudentCourse studentCourse) {

        Course course = this.courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Student student = this.studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        StudentCourse existingStudentCourse = this.studentCourseRepository.findByIdAndCourseAndStudent(id, course, student);
        if (existingStudentCourse == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("StudentCourse not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        existingStudentCourse.setGrade(studentCourse.getGrade());
        this.studentCourseRepository.save(existingStudentCourse);
        StudentCourseResponse studentCourseResponse = new StudentCourseResponse();
        studentCourseResponse.set(existingStudentCourse);

        int averageGrade = 0;
        int notNullGrades = 0;
        for (StudentCourse currentStudentCourse: this.studentCourseRepository.findByStudent(student)) {
            System.out.println(currentStudentCourse.getGrade());
            if (currentStudentCourse.getGrade() != 0) {
                averageGrade += currentStudentCourse.getGrade();
                notNullGrades += 1;
            }
        }
        if (notNullGrades != 0) {
            averageGrade /= notNullGrades;
            student.setAverageGrade(averageGrade);
        }
        this.studentRepository.save(student);

        return new ResponseEntity<>(studentCourseResponse, HttpStatus.CREATED);
    }
}