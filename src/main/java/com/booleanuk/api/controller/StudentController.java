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
@RequestMapping("students")
public class StudentController {
    @Autowired
    private final StudentRepository repository;

    @Autowired
    private final CourseRepository courseRepository;

    public StudentController(StudentRepository repository, CourseRepository courseRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create (@RequestBody Student studentDetails) {
        if(studentDetails.isInvalid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a student with the specified parameters.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Student student = new Student(
                studentDetails.getFirstName(),
                studentDetails.getLastName(),
                studentDetails.getDob(),
                studentDetails.getAvgGrade());
        ApiResponse<Student> response = new ApiResponse<>("success", repository.save(student));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> readAll() {
        List<Student> students = this.repository.findAll();
        ApiResponse<List<Student>> response = new ApiResponse<>("success", students);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> readOne (@PathVariable int id) {
        Optional<Student> stu = this.repository.findById(id);

        if(stu.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find student with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Student student = stu.get();
        ApiResponse<Student> response = new ApiResponse<>("success", student);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> update (@PathVariable int id, @RequestBody Student studentDetails) {
        if(studentDetails.isInvalid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a student with the specified parameters.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Student> stu = this.repository.findById(id);

        if(stu.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find student with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Student student = stu.get();
        student.update(
                studentDetails.getFirstName(),
                studentDetails.getLastName(),
                studentDetails.getDob(),
                studentDetails.getCourse(),
                studentDetails.getAvgGrade());
        ApiResponse<Student> response = new ApiResponse<>("success", repository.save(student));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{studentId}/enroll/{courseId}")
    public ResponseEntity<ApiResponse<?>> enroll (@PathVariable int studentId, @PathVariable int courseId) {
        Optional<Student> stu = this.repository.findById(studentId);

        if(stu.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find student with id " + studentId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Student student = stu.get();
        Optional<Course> cou = this.courseRepository.findById(courseId);

        if(cou.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find course with id " + courseId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Course course = cou.get();
        student.setCourse(course);
        course.getStudents().add(student);
        this.repository.save(student);
        this.courseRepository.save(course);

        ApiResponse<Student> response = new ApiResponse<>("success", student);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete (@PathVariable int id) {


        Optional<Student> stu = this.repository.findById(id);

        if(stu.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not find student with id " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Student student = stu.get();
        this.repository.delete(student);

        ApiResponse<Student> response = new ApiResponse<>("success", student);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
