package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;

import com.booleanuk.api.view.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

	private final StudentRepository studentRepository;

	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	// GET: Retrieve all students
	@GetMapping
	public ResponseEntity<List<Student>> getAll() {
		List<Student> students = studentRepository.findAll();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	// GET {id}: Retrieve a student by ID
	@GetMapping("{id}")
	public ResponseEntity<Student> getOne(@PathVariable Integer id) {
		return studentRepository.findById(id)
				.map(student -> new ResponseEntity<>(student, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// POST: Create a new student
	@PostMapping
	public ResponseEntity<Student> postOne(@RequestBody Student student) {
		if (isValidStudent(student)) {
			Student createdStudent = studentRepository.save(student);
			return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping("{id}")
	public ResponseEntity<Student> putOne(@PathVariable Integer id, @RequestBody Student updatedStudent) {
		return studentRepository.findById(id)
				.map(existingStudent -> {
					updateStudentDetails(existingStudent, updatedStudent);
					studentRepository.save(existingStudent);
					return new ResponseEntity<>(existingStudent, HttpStatus.OK);
				})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
		return studentRepository.findById(id)
				.map(student -> {
					studentRepository.delete(student);
					return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	private boolean isValidStudent(Student student) {
		return student.getFirstName() != null && !student.getFirstName().isEmpty() &&
				student.getLastName() != null && !student.getLastName().isEmpty() &&
				student.getCourseTitle() != null && student.getDateOfBirth() != null &&
				student.getStartDateforCourse() != null && student.getAverageGrade() != null;
	}

	private void updateStudentDetails(Student existingStudent, Student updatedStudent) {
		existingStudent.setFirstName(updatedStudent.getFirstName());
		existingStudent.setLastName(updatedStudent.getLastName());
		existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
		existingStudent.setCourseTitle(updatedStudent.getCourseTitle());
		existingStudent.setStartDateforCourse(updatedStudent.getStartDateforCourse());
		existingStudent.setAverageGrade(updatedStudent.getAverageGrade());
	}
}
