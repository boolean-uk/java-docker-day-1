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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllStudents() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneStudent(@PathVariable int id)  {
        Student student = this.studentRepository.findById(id).orElse(null);
        if(student == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        return ResponseEntity.ok(studentResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student)  {
        if(student.getFirstName() == null
        || student.getLastName() == null
        || student.getDob() == null
        || student.getAvgGrade() == 0)  {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        this.studentRepository.save(student);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(student);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student)    {
        if(student.getFirstName() == null
                || student.getLastName() == null
                || student.getDob() == null
                || student.getAvgGrade() == 0)  {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);
        if(studentToUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());
        studentToUpdate.setAvgGrade(student.getAvgGrade());
        List<Course> courses = new ArrayList<>();

        // Remove student from courses they are already in...
        for(Course ids : studentToUpdate.getCourses())
        {
            Course tempCourse = this.courseRepository.findById(ids.getId()).orElse(null);
            if(tempCourse == null)  {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            tempCourse.getStudents().remove(studentToUpdate);
        }
        // Then add them to the courses again
        for(Course ids : student.getCourses())
        {
            Course tempCourse = this.courseRepository.findById(ids.getId()).orElse(null);
            if(tempCourse == null)  {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            courses.add(tempCourse);
        }

        studentToUpdate.setCourses(courses);
        this.studentRepository.save(studentToUpdate);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id)  {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);
        if(studentToDelete == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        for(Course ids : studentToDelete.getCourses())
        {
            Course tempCourse = this.courseRepository.findById(ids.getId()).orElse(null);
            if(tempCourse == null)  {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            tempCourse.getStudents().remove(studentToDelete);
        }
        this.studentRepository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }
}