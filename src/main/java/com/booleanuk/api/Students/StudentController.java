package com.booleanuk.api.Students;

import com.booleanuk.api.Responses.ErrorResponse;
import com.booleanuk.api.Responses.Response;
import com.booleanuk.api.Responses.StudentListResponse;
import com.booleanuk.api.Responses.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping
    public ResponseEntity<StudentListResponse> getAllStudents() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        StudentResponse studentResponse = new StudentResponse();
        try {
            studentResponse.set(this.repository.save(student));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
        Student item = this.repository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(item);
        return ResponseEntity.ok(studentResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.repository.findById(id).orElse(null);
        if (studentToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());

        try {
            studentToUpdate = this.repository.save(studentToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        StudentResponse itemResponse = new StudentResponse();
        itemResponse.set(studentToUpdate);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Response<?>> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.repository.findById(id).orElse(null);
        if (studentToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(studentToDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }


}
