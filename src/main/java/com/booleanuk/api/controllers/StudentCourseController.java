package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.models.StudentCourse;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.StudentCourseRepository;
import com.booleanuk.api.repositories.StudentRepository;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student-courses")
public class StudentCourseController extends GenericController<StudentCourse, Integer> {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody StudentCourse studentCourse) {
        Response<Object> response = new Response<>();
        Optional<StudentCourse> studentCourseOptional = studentCourseRepository.findById(id);
        Optional<Student> studentOptional = studentRepository.findById(studentCourse.getStudent().getId());
        Optional<Course> courseOptional = courseRepository.findById(studentCourse.getCourse().getId());
        if (studentCourseOptional.isEmpty() || studentOptional.isEmpty() || courseOptional.isEmpty()) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        StudentCourse existingStudentCourse = studentCourseOptional.get();
        existingStudentCourse.setCourse(courseOptional.get());
        existingStudentCourse.setStudent(studentOptional.get());
        existingStudentCourse.setGrade(studentCourse.getGrade());

        try {
            StudentCourse updatedEntity = studentCourseRepository.save(existingStudentCourse);

            // Update the student's averageGrade
            Student student = updatedEntity.getStudent();
            student.calculateAndUpdateAverageGrade();
            studentRepository.save(student);

            response.setSuccess(updatedEntity);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("Bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
