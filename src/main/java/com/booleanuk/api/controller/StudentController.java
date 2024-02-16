package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.SuccessResponse;
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
    public ResponseEntity<Response<List<Student>>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(this.studentRepository.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        if(containsNull(student)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }

        Course tempCourse = courseRepository.findById(student.getCourse().getId()).orElse(null);

        if(tempCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        student.setCourse(tempCourse);

        Response<Student> response = new SuccessResponse<>(studentRepository.save(student));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = findStudent(id);
        if(studentToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        studentRepository.delete(studentToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(studentToDelete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = findStudent(id);
        if(studentToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(student.getFirstName() != null) {
            studentToUpdate.setFirstName(student.getFirstName());
        }
        if(student.getLastName() != null) {
            studentToUpdate.setLastName(student.getLastName());
        }
        if(student.getDob() != null) {
            studentToUpdate.setDob(student.getDob());
        }
        if(student.getGrade() != null) {
            studentToUpdate.setGrade(student.getGrade());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(studentRepository.save(studentToUpdate)));
    }

    private Student findStudent(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Student student) {
        return student.getFirstName() == null || student.getLastName() == null || student.getDob() == null || student.getGrade() == null || student.getCourse() == null;
    }
}


