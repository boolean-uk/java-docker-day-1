package com.booleanuk.api.cotroller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.payload.request.StudentRequest;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.payload.response.StudentListResponse;
import com.booleanuk.api.payload.response.StudentResponse;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllStudents() {
        StudentListResponse studentListResponse = new StudentListResponse();
        studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody StudentRequest studentRequest){
        StudentResponse studentResponse = new StudentResponse();

        Course course = this.courseRepository.findById(studentRequest.getCourseId()).orElse(null);
            if (course == null){
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        Student createStudent = new Student(studentRequest.getFirstName(), studentRequest.getLastName(), studentRequest.getDob());
            createStudent.setCourse(course);

            try {
                studentResponse.set(this.studentRepository.save(createStudent));
            } catch (Exception e){
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Bad request");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody StudentRequest student){
        Student studentUpdate = this.studentRepository.findById(id).orElse(null);
        Course course = this.courseRepository.findById(id).orElse(null);
        if (studentUpdate == null || course == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        studentUpdate.setFirstName(student.getFirstName());
        studentUpdate.setLastName(student.getLastName());
        studentUpdate.setDob(student.getDob());
        studentUpdate.setCourse(course);

        try {
            studentUpdate = this.studentRepository.save(studentUpdate);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Student studentDelete = this.studentRepository.findById(id).orElse(null);
        if (studentDelete == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found student");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(studentDelete);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentDelete);
        return ResponseEntity.ok(studentResponse);
    }
}
