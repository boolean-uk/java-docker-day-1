package com.booleanuk.api.enrollments;

import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import com.booleanuk.api.courses.Course;
import com.booleanuk.api.courses.CourseRepository;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.student.Student;
import com.booleanuk.api.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students/{studentId}/enrollments")
public class EnrollmentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @GetMapping()
    public ResponseEntity<?> getAll(@PathVariable int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + studentId + " found")
        );
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        Response<List<?>> response = new Response<>();
        response.setResponse("Success", enrollments);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<?> createEnrollment(@PathVariable int studentId, @PathVariable int courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + studentId + " found")
        );

        Course course = this.courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + courseId + " found")
        );

        try{
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setStudent(student);
            enrollmentRepository.save(enrollment);
            Response<Enrollment> response = new Response<>();
            response.setResponse("success", enrollment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad Request");
        }
    }
}
