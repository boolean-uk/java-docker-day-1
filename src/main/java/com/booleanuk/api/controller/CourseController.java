package com.booleanuk.api.controller;

import com.booleanuk.api.model.ApiResponse;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private final CourseRepository repository;

    @Autowired
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create (@RequestBody Course courseDetails) {
        if (courseDetails.isInvalid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a course with the specified parameters.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Course course = new Course(courseDetails.getTitle(), courseDetails.getCourseStartDate());
        ApiResponse<Course> response = new ApiResponse<>("success", repository.save(course));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> readAll() {
        List<Course> courses = this.repository.findAll();
        ApiResponse<List<Course>> response = new ApiResponse<>("success", courses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> readOne (@PathVariable int id) {
        Optional<Course> cou = this.repository.findById(id);

        if(cou.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find course with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Course course = cou.get();
        ApiResponse<Course> response = new ApiResponse<>("success", course);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> update (@PathVariable int id, @RequestBody Course courseDetails) {
        if(courseDetails.isInvalid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a course with the specified parameters.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Course> cou = this.repository.findById(id);

        if(cou.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find course with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Course course = cou.get();
        course.update(courseDetails.getTitle(), courseDetails.getCourseStartDate());
        ApiResponse<Course> response = new ApiResponse<>("success", repository.save(course));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete (@PathVariable int id) {


        Optional<Course> cou = this.repository.findById(id);

        if(cou.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find course with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Course course = cou.get();
        for (Student s: course.getStudents()) {
            s.setCourse(null);
            studentRepository.save(s);
        }
        course.setStudents(null);
        this.repository.delete(course);

        ApiResponse<Course> response = new ApiResponse<>("success", course);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
