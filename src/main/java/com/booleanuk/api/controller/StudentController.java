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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Student> getAll(){
        return this.studentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Student> createEmployee(@RequestBody Student employee){
        return new ResponseEntity<Student>(this.studentRepository.save(employee), HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id){
        Student employee = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete(@PathVariable int id) {
        Student toDelete = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.studentRepository.delete(toDelete);
        toDelete.setCourses(new ArrayList<Course>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student employee){
        Student update = this.studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        update.setFirstName(employee.getFirstName());
        update.setLastName(employee.getLastName());
        update.setEmail(employee.getEmail());
        update.setCourses(new ArrayList<>());
        return new ResponseEntity<>(this.studentRepository.save(update), HttpStatus.CREATED);
    }
}
