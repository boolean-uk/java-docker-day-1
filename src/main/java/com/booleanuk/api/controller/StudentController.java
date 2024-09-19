package com.booleanuk.api.controller;

import com.booleanuk.api.dto.StudentDTO;
import com.booleanuk.api.dto.StudentMapper;
import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.CourseRepository;
import com.booleanuk.api.repository.StudentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentMapper studentMapper;

    @PostMapping
    StudentDTO addStudent(@Valid @RequestBody StudentDTO studentDTO, BindingResult result) throws ResponseStatusException {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more values missing. Please try again.");
        }

        Student savedStudent = this.studentRepository.save(studentMapper.toEntity(studentDTO));
        return this.studentMapper.toDTO(savedStudent);
    }

    @GetMapping
    List<StudentDTO> getAllStudents() {
        List<StudentDTO> students = new ArrayList<>();
        this.studentRepository.findAll().forEach(student -> students.add(this.studentMapper.toDTO(student)));
        return students;
    }

    @GetMapping("/{id}")
    StudentDTO getStudentById(@PathVariable (name = "id") int id) throws ResponseStatusException {
        return this.studentRepository.findById(id)
                .map(studentMapper::toDTO)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with provided ID does not exist"));
    }

    @PutMapping("/{id}")
    StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable (name = "id") int id) throws ResponseStatusException {
        Student studentToUpdate = this.studentRepository.findById(id)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with provided ID does not exist."));

        update(studentToUpdate, studentDTO);

        Student savedStudent = this.studentRepository.save(studentToUpdate);

        return this.studentMapper.toDTO(savedStudent);
    }

    @Transactional
    /*
    Ensures that the entire method runs within a transaction,
    keeping the session open until the method completes.
     */
    @DeleteMapping("/{id}")
    StudentDTO deleteStudent(@PathVariable (name = "id") int id) throws ResponseStatusException {
        return this.studentRepository.findById(id).map(studentToDelete -> {
            this.studentRepository.delete(studentToDelete);
            return this.studentMapper.toDTO(studentToDelete);
        }).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with provided ID does not exist."));
    }

    @PutMapping("/{studentId}/courses/{courseId}")
    StudentDTO addCourseToStudent(
            @PathVariable (name = "studentId") int studentId,
            @PathVariable (name = "courseId") int courseId) throws ResponseStatusException {
        Student studentToUpdate = this.studentRepository.findById(studentId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with provided ID does not exist."));

        Course courseToAdd = this.courseRepository.findById(courseId).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course with provided ID does not exist."));

        studentToUpdate.getCourses().add(courseToAdd);
        Student savedStudent = this.studentRepository.save(studentToUpdate);

        return this.studentMapper.toDTO(savedStudent);
    }

    private void update(Student studentToUpdate, StudentDTO updatedStudent){
        studentToUpdate.setFirstName(updatedStudent.getFirstName());
        studentToUpdate.setLastName(updatedStudent.getLastName());
        studentToUpdate.setDateOfBirth(updatedStudent.getDateOfBirth());
    }
}
