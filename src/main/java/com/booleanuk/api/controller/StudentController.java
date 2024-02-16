package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.payload.response.ErrorMessage;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;

	@GetMapping
	public ResponseEntity<Response<List<Student>>> getAllStudents() {
		return ResponseEntity.ok(new Response<>("success", studentRepository.findAll()));
	}

	@PostMapping
	public ResponseEntity<Response<?>> createStudent(Student student) {
		Student studentNew = new Student(student.getFirstName(), student.getFirstName(), student.getDateOfBirth(), student.getCourseTitle(), student.getStartDateForCourse(), student.getAverageGrade());
		studentRepository.save(student);
		return new ResponseEntity<>(new Response<>("success", studentNew), HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
		Student student = studentRepository.findById(id).orElse(null);
		if (student == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("not found")), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new Response<>("success", student));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, Student student) {
		Student studentNew = studentRepository.findById(id).orElse(null);
		if (studentNew == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("not found")), HttpStatus.NOT_FOUND);
		}
		if (student.getFirstName() != null) {
			studentNew.setFirstName(student.getFirstName());
		}
		if (student.getLastName() != null) {
			studentNew.setLastName(student.getLastName());
		}
		if (student.getDateOfBirth() != null) {
			studentNew.setDateOfBirth(student.getDateOfBirth());
		}
		if (student.getCourseTitle() != null) {
			studentNew.setCourseTitle(student.getCourseTitle());
		}
		if (student.getStartDateForCourse() != null) {
			studentNew.setStartDateForCourse(student.getStartDateForCourse());
		}
		if (student.getAverageGrade() != 0) {
			studentNew.setAverageGrade(student.getAverageGrade());
		}
		studentRepository.save(studentNew);
		return new ResponseEntity<>(new Response<>("success", studentNew), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<?>> deleteStudentById(@PathVariable int id) {
		Student student = studentRepository.findById(id).orElse(null);
		if (student == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("not found")), HttpStatus.NOT_FOUND);
		}
		studentRepository.delete(student);
		return ResponseEntity.ok(new Response<>("success", student));

	}
}

