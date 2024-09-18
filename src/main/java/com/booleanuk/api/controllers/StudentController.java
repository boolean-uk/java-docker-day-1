package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){


        try {

            return new ResponseEntity<Student>(this.studentRepository.save(student),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create student, please check all required fields are correct.");
        }


    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable int id){
        Student student=this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id,
                                           @RequestBody Student student){
        Student studentToUpdate=this.studentRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No student with that ID found")
        );
        studentToUpdate.setFirstName(student.getLastName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());
        studentToUpdate.setCourse(student.getCourse());
        studentToUpdate.setStartDate(student.getStartDate());
        studentToUpdate.setAvgGrade(student.getAvgGrade());
        return new ResponseEntity<Student>(this.studentRepository.save(studentToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id){
        Student studentToDelete=this.studentRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No student with that ID found")
        );

        this.studentRepository.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }
}
