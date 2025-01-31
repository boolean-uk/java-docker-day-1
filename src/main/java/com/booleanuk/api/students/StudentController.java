package com.booleanuk.api.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Student getOneStudent(@PathVariable int id) {
        return this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.")
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Student createStudent(@RequestBody Student student) {

        Student newStudent = new Student(student.getFirstName(), student.getLastName(), student.getDateOfBirth(), student.getCourseTitle(), student.getCourseStartDate(), student.getAverageGrade());
        return this.studentRepository.save(newStudent);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")
        );

        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setCourseTitle(student.getCourseTitle());
        studentToUpdate.setCourseStartDate(student.getCourseStartDate());
        studentToUpdate.setAverageGrade(student.getAverageGrade());
        return this.studentRepository.save(studentToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.")
        );

        this.studentRepository.delete(studentToDelete);
        return studentToDelete;
    }
}