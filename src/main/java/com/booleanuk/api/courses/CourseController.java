package com.booleanuk.api.courses;

import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Course> customers = courseRepository.findAll();
        Response<List<Course>> response = new Response<>(
                "success",
                customers);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable int id) {
        Course course = findById(id);
        Response<Course> response = new Response<>(
                "success",
                course);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course) {

        try{
            this.courseRepository.save(course);
            Response<Course> response = new Response<>(
                    "success",
                    course);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable int id) {
        Course courseToUpdate = findById(id);
        try{
            courseToUpdate.setCourseName(course.getCourseName());
            courseToUpdate.setStartDate(course.getStartDate());
            this.courseRepository.save(courseToUpdate);
            Response<Course> response = new Response<>(
                    "success",
                    courseToUpdate);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Course course = findById(id);
        this.courseRepository.delete(course);

        Response<Course> response = new Response<>(
                "success",
                course);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private Course findById(int id) {
        return this.courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer not found with ID " + id)
        );

    }

}
