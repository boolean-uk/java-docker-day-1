package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import com.booleanuk.api.models.Module;
import com.booleanuk.api.models.Student;
import com.booleanuk.api.repositories.CourseRepository;
import com.booleanuk.api.repositories.ModuleRepository;
import com.booleanuk.api.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get a specific course by ID
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Get all students for a specific course by ID
    @GetMapping("/{courseId}/students")
    public List<Student> getStudentsInCourse(@PathVariable String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return course.getStudents();
    }

    // Get all modules for a specific course by ID
    @GetMapping("/{courseId}/modules")
    public List<Module> getModulesInCourse(@PathVariable String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return course.getModules();
    }

    // Create a new course
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    // Update an existing course
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @RequestBody Course updatedCourse) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(updatedCourse.getTitle());
                    course.setStartDate(updatedCourse.getStartDate());
                    course.setModules(updatedCourse.getModules());
                    course.setStudents(updatedCourse.getStudents());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        if (courseRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        courseRepository.deleteById(id);
    }

    // Add a student to a course
    @PostMapping("/{courseId}/students/{studentId}")
    public void addStudentToCourse(@PathVariable String courseId, @PathVariable UUID studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Student> students = course.getStudents();
        students.add(student);
        course.setStudents(students);
        student.setCourse(course);

        courseRepository.save(course);
    }

    // Remove a student from a course
    @DeleteMapping("/{courseId}/students/{studentId}")
    public void removeStudentFromCourse(@PathVariable String courseId, @PathVariable UUID studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Student> students = course.getStudents();
        students.removeIf(student -> student.getId().equals(studentId));
        course.setStudents(students);
        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .setCourse(null);

        courseRepository.save(course);
    }

    // Add a module to a course
    @PostMapping("/{courseId}/modules/{moduleId}")
    public void addModuleToCourse(@PathVariable String courseId, @PathVariable UUID moduleId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Module> modules = course.getModules();
        modules.add(module);
        course.setModules(modules);

        courseRepository.save(course);
    }

    // Remove a module from a course
    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public void removeModuleFromCourse(@PathVariable String courseId, @PathVariable UUID moduleId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Module> modules = course.getModules();
        modules.removeIf(module -> module.getId().equals(moduleId));
        course.setModules(modules);

        courseRepository.save(course);
    }
}
