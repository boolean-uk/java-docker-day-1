package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    StudentRepository repository;

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = this.repository.findAll();

        return ResponseEntity.ok(students);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getById(@PathVariable Integer id) {
        Student student = this.repository.findById(id).orElse(null);

        if(student == null){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(student);
    }


    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        this.repository.save(student);

        return ResponseEntity.ok(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable Integer id, @RequestBody Student student){
        Student oldStudent = this.repository.findById(id).orElse(null);

        if(oldStudent != null) {
            oldStudent.setFirstName(student.getFirstName());
            oldStudent.setLastName(student.getLastName());
            oldStudent.setDob(student.getDob());
            oldStudent.setGrades(student.getGrades());

            this.repository.save(oldStudent);

            return ResponseEntity.ok(oldStudent);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Integer id) {
        Student deleteStudent = this.repository.findById(id).orElse(null);

        if(deleteStudent != null) {
            this.repository.delete(deleteStudent);
            return ResponseEntity.ok(deleteStudent);
        }

        return ResponseEntity.badRequest().body(null);
    }
}
