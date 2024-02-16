package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        List<Student> students = this.studentRepository.findAll();
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(students);
        return ResponseEntity.ok(studentListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
        Student returnStudent = this.studentRepository.findById(id).orElse(null);
        if (returnStudent == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No students matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(returnStudent);
        return ResponseEntity.ok(studentResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        if (student.getFirstName() == null || student.getLastName() == null
            || student.getDateOfBirth() == null || student.getCourse() == null
            || student.getAverageGrade() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the student, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Course tempCourse = this.courseRepository.findById(student.getCourse().getId()).orElse(null);

        if(tempCourse == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No courses matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        student.setCourse(tempCourse);

        Student createdStudent = this.studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(createdStudent);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (student.getFirstName() == null || student.getLastName() == null
                || student.getDateOfBirth() == null || student.getCourse() == null
                || student.getAverageGrade() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the student's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Course tempCourse = this.courseRepository.findById(student.getCourse().getId()).orElse(null);

        if(tempCourse == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No courses matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        student.setCourse(tempCourse);

        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);

        if(studentToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No students matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setCourse(student.getCourse());
        studentToUpdate.setAverageGrade(student.getAverageGrade());

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);

        if(studentToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No students matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        this.studentRepository.delete(studentToDelete);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }

}
