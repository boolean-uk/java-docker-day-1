package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.StudentRepository;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllStudents(){
        Response<List<Student>> response = new Response<>();
        response.set(this.studentRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getAStudent(@PathVariable int id){
        Student student = findTheStudent(id);
        if (student == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<Student> response = new Response<>();
        response.set(student);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestParam int course_id,@RequestBody Student student){
        Course findCourse = this.courseRepository.findById(course_id)
                .orElse(null);

        if (findCourse == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (student.getFirst_name() == null ||
        student.getLast_name()== null ||
        student.getDOB() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        student.setCourse(findCourse);
        Response<Student> response = new Response<>();
        response.set(this.studentRepository.save(student));
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id,
                                                     @RequestParam int course_id, @RequestBody Student student){
        Student updateStudent = findTheStudent(id);
        Course findCourse = this.courseRepository.findById(course_id)
                .orElse(null);

        if (findCourse == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("course not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (updateStudent == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (student.getFirst_name() == null ||
                student.getLast_name()== null ||
                student.getDOB() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        updateStudent.setFirst_name(student.getFirst_name());
        updateStudent.setLast_name(student.getLast_name());
        updateStudent.setDOB(student.getDOB());
        updateStudent.setCourse(findCourse);

        Response<Student> response = new Response<>();
        response.set(this.studentRepository.save(updateStudent));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteDelete(@PathVariable int id){
        Student deleteStudent = findTheStudent(id);
        if (deleteStudent == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("student not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(deleteStudent);
        Response<Student> response = new Response<>();
        response.set(deleteStudent);
        return ResponseEntity.ok(response);
    }


    private Student findTheStudent(int id ){
        return this.studentRepository.findById(id)
                .orElse(null);
    }

}
