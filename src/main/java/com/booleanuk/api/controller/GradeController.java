package com.booleanuk.api.controller;

import com.booleanuk.api.dto.GradeDTO;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Grade;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.GradeRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("grades")
public class GradeController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GradeRepository repository;


    @PostMapping()
    public ResponseEntity<Grade> addGrade(@RequestBody GradeDTO gradeDTO) {
        Student student = this.studentRepository.findById(gradeDTO.getStudent()).orElse(null);
        Course course = this.courseRepository.findById(gradeDTO.getCourse()).orElse(null);

        if(student == null || course == null){
            return ResponseEntity.badRequest().body(null);
        }

        Grade grade = new Grade(student, course, gradeDTO.getGrade());

        student.addGrade(grade);
        course.addGrade(grade);

        this.repository.save(grade);

        this.studentRepository.save(student);
        this.courseRepository.save(course);

        return ResponseEntity.ok(grade);
    }

}
