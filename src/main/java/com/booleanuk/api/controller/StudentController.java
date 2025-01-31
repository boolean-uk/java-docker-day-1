package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getOne(@PathVariable int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = studentRepository.findById(id).orElseThrow();
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDateOfBirth(student.getDateOfBirth());
        studentToUpdate.setAverageGrade(student.getAverageGrade());
        return studentRepository.save(studentToUpdate);
    }

    @GetMapping("/{id}/courses")
    public Set<Course> getStudentsCourses(@PathVariable int id) {
        return studentRepository.findById(id).orElseThrow().getMyCourses();
    }

    @PostMapping("/{s_id}")
    public Course deleteStudentCourse(@PathVariable(name="s_id") int studentId, @RequestBody Course course) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        if (student.getMyCourses().add(course)) {
            studentRepository.save(student);
            return courseRepository.findById(course.getId()).orElseThrow();
        }
        return null;
    }

    @DeleteMapping("/{s_id}/course/{c_id}")
    public Course deleteStudentCourse(@PathVariable(name="s_id") int studentId, @PathVariable(name="c_id") int courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        if (student.getMyCourses().removeIf(c -> c.getId() == courseId)) {
            studentRepository.save(student);
            return courseRepository.findById(courseId).orElseThrow();
        }
        return null;
    }

    @DeleteMapping
    public Student delete(@PathVariable int id) {
        Student s = studentRepository.findById(id).orElseThrow();
        studentRepository.deleteById(id);
        return s;
    }
}
