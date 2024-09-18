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
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping

    public ResponseEntity<Course> createCourse(@RequestBody Course course){


        try {

            return new ResponseEntity<Course>(this.courseRepository.save(course),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create course, please check all required fields are correct.");
        }


    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(this.courseRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getOneCourse(@PathVariable int id){
        Course course=this.courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(course);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id,
                                                 @RequestBody Course course){
        Course courseToUpdate=this.courseRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No course with that ID found")
        );
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setTeacher(course.getTeacher());
        courseToUpdate.setStartDate(course.getStartDate());
        return new ResponseEntity<Course>(this.courseRepository.save(courseToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id){
        Course courseToDelete=this.courseRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No course with that ID found")
        );

        this.courseRepository.delete(courseToDelete);
        return ResponseEntity.ok(courseToDelete);
    }
}
