package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
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

    @Autowired
    private CourseRepository courseRepository;
    @GetMapping
    public List<Student> getAllStudents(){
        return this.studentRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) {
        Student student = this.studentRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find student"));

        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        //Regex for the strings
        String regex = "^[a-zA-Z\\s]+$";
        if(!student.getFirstName().matches(regex) || !student.getLastName().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }

        Course course = this.courseRepository.findById(student.getCourse().getId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the course...."));

        student.setCourse(course);

        return new ResponseEntity<>(this.studentRepository.save(student), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateAStudent(@PathVariable int id,@RequestBody Student student){
        Student studentToUpdate = this.studentRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the student...."));

        Course course = this.courseRepository.findById(student.getCourse().getId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the course...."));

        studentToUpdate.setCourse(course);

        //Regex for the strings
        String regex = "^[a-zA-Z\\s]+$";

        if(!student.getFirstName().matches(regex) || !student.getLastName().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setAverageGrade(student.getAverageGrade());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());

        return new ResponseEntity<>(this.studentRepository.save(studentToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteAStudent(@PathVariable int id){
        Student studentToDelete = this.studentRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the student!!!"));
        this.studentRepository.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }
}

