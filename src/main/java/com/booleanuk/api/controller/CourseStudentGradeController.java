package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.CourseStudentGrade;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.CourseStudentGradeRepository;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.responses.CourseStudentGradeResponse;
import com.booleanuk.api.responses.ErrorResponse;
import com.booleanuk.api.responses.Response;
import com.booleanuk.api.responses.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("studentcourses")
public class CourseStudentGradeController {
    @Autowired
    private CourseStudentGradeRepository courseStudentGradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;



    @PostMapping("{sId}/course/{cId}")
    public ResponseEntity<Response<?>> addStudentToCourse(@PathVariable int sId, @PathVariable int cId) {
        Student studentToUpdate = this.studentRepository.findById(sId).orElse(null);
        Course courseToUpdate = this.courseRepository.findById(cId).orElse(null);

        if(studentToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Entered student-ID does not exist");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else if (courseToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Entered course-ID does not exist");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(CourseStudentGrade courseStudentGrade : this.courseStudentGradeRepository.findAll()){
            if(courseStudentGrade.getStudent().equals(studentToUpdate) && courseStudentGrade.getCourse().equals(courseToUpdate)){
                ErrorResponse error = new ErrorResponse();
                error.set("bad request, student already attends this course!");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

            }
        }

        CourseStudentGrade courseStudentGrade = new CourseStudentGrade(studentToUpdate, courseToUpdate, 0);
        this.courseStudentGradeRepository.save(courseStudentGrade);
        this.studentRepository.save(studentToUpdate);


        StudentResponse studentResponse = new StudentResponse();
        studentResponse.set(studentToUpdate);

        return ResponseEntity.ok(studentResponse);
    }




    @DeleteMapping("{sId}/course/{cId}")
    public ResponseEntity<Response<?>> removeStudentFromCourse(@PathVariable int sId, @PathVariable int cId) {
        Student studentToUpdate = this.studentRepository.findById(sId).orElse(null);
        Course courseToUpdate = this.courseRepository.findById(cId).orElse(null);

        if(studentToUpdate == null || courseToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CourseStudentGradeResponse courseStudentGradeResponse = new CourseStudentGradeResponse();
        for(CourseStudentGrade courseStudentGrade : this.courseStudentGradeRepository.findAll()){
            if(courseStudentGrade.getStudent().equals(studentToUpdate) && courseStudentGrade.getCourse().equals(courseToUpdate)){
                this.courseStudentGradeRepository.delete(courseStudentGrade);
                courseStudentGradeResponse.set(courseStudentGrade);
                return ResponseEntity.ok(courseStudentGradeResponse);

            }
        }
        ErrorResponse error = new ErrorResponse();
        error.set("bad request, student do not attend this course!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    @PutMapping("{sId}/course/{cId}")
    public ResponseEntity<Response<?>> updateGrade(@PathVariable int sId, @PathVariable int cId, @RequestBody CourseStudentGrade request) {
        Student studentToUpdate = this.studentRepository.findById(sId).orElse(null);
        Course courseToUpdate = this.courseRepository.findById(cId).orElse(null);

        if(studentToUpdate == null || courseToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CourseStudentGradeResponse courseStudentGradeResponse = new CourseStudentGradeResponse();
        for(CourseStudentGrade courseStudentGrade : this.courseStudentGradeRepository.findAll()){
            if(courseStudentGrade.getStudent().equals(studentToUpdate) && courseStudentGrade.getCourse().equals(courseToUpdate)){
                courseStudentGrade.setGrade(request.getGrade());
                this.courseStudentGradeRepository.save(courseStudentGrade);
                courseStudentGradeResponse.set(courseStudentGrade);
                return ResponseEntity.ok(courseStudentGradeResponse);

            }
        }
        ErrorResponse error = new ErrorResponse();
        error.set("bad request, student do not attend this course!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
