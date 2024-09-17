package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.CourseRepository;
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
    protected record PostStudent(Integer id, String firstName, String lastName, String dateOfBirth, Course course, Integer courseId) {
        public boolean haveNullFields() {
            return firstName == null || lastName == null || dateOfBirth == null;
        }
    }

    @Autowired
    private StudentRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody PostStudent request) {
        if (request.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Course _course = request.course;

        if (_course != null)
            courseRepository.save(_course);
        else if (request.courseId != null)
            _course = courseRepository.findById(request.courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        return new ResponseEntity<>(repository.save(new Student(request.id, request.firstName, request.lastName, request.dateOfBirth, _course)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable final Integer id, @RequestBody final PostStudent model) {
        if (model.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Student _targetModel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetModel.setFirstName(model.firstName);
        _targetModel.setLastName(model.lastName);
        _targetModel.setDateOfBirth(model.dateOfBirth);

        Course _course = model.course;

        if (_course != null)
            courseRepository.save(_course);
        else if (model.courseId != null)
            _course = courseRepository.findById(model.courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetModel.setCourse(_course);

        return new ResponseEntity<>(repository.save(_targetModel), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
