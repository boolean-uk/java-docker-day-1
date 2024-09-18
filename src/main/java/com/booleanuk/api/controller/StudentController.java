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


@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private final StudentRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentController(StudentRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    /*@GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow());
    }*/

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        Course course = this.courseRepository.findById(student.getCourse().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course found..")
        );
        student.setCourse(course);
        return new ResponseEntity<>(this.repository.save(student),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Integer id,
                                             @RequestBody Student student) {
        Student studentToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing student with that ID found")
        );
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDob(student.getDob());
        return new ResponseEntity<>(this.repository.save(studentToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id) {
        Student studentToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No student with that ID were found")
        );
        this.repository.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }
}
