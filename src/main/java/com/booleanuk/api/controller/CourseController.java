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
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable int id){
        Course employee = this.courseRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }
    @GetMapping
    public List<Course> getAll(){
        return this.courseRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course book){
        //if you have many to one relation in employee 'class for department, then
        //you need to do it like this, by making a temp department.
        Student tempAuthor = studentRepository.findById(book.getStudent()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        book.setStudent(tempAuthor);

        return ResponseEntity.ok(courseRepository.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable int id){
        Course delete = this.courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.courseRepository.delete(delete);
        return ResponseEntity.ok(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateEmployee(@PathVariable int id, @RequestBody Course book){
        Course update = this.courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        Student tempAuthor = studentRepository.findById(book.getStudent()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        update.setStudent(tempAuthor);
        update.setTitle(book.getTitle());
        update.setGenre(book.getGenre());
        return new ResponseEntity<>(this.courseRepository.save(update), HttpStatus.CREATED);
    }
}
