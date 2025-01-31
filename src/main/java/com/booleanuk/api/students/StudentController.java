package com.booleanuk.api.students;


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
    StudentRepository studentRepository;


    @GetMapping
    ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(this.studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Student> getOneStudent(@PathVariable(name = "id") int id) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(this.studentRepository.save(student), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        studentToUpdate.setFirst_name(student.getFirst_name());
        studentToUpdate.setLast_name(student.getLast_name());
        studentToUpdate.setCourse_title(student.getCourse_title());
        studentToUpdate.setDob(student.getDob());
        studentToUpdate.setStart_date(student.getStart_date());
        studentToUpdate.setAverage_grade(student.getAverage_grade());

        return new ResponseEntity<>(this.studentRepository.save(studentToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        this.studentRepository.delete(studentToDelete);
        return ResponseEntity.ok(studentToDelete);
    }
}
