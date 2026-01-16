package com.booleanuk.api.controller;


import com.booleanuk.api.model.Course;
import com.booleanuk.api.payload.response.ErrorMessage;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping
	public ResponseEntity<Response<List<Course>>> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		return ResponseEntity.ok(new Response<>("success", courses));
	}

	@PostMapping
	public ResponseEntity<Response<?>> createCourse(@RequestBody Course course) {
		courseRepository.save(course);
		return new ResponseEntity<>(new Response<>("success", course), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<?>> getCourseById(@PathVariable int id) {
		Course course = courseRepository.findById(id).orElse(null);
		if (course == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("Course not found")), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new Response<>("success", course));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<?>> updateCourse(@PathVariable int id, @RequestBody Course course) {
		Course existingCourse = courseRepository.findById(id).orElse(null);
		if (existingCourse == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("Course not found")), HttpStatus.NOT_FOUND);
		}
		existingCourse.setTitle(course.getTitle());
		existingCourse.setStartDate(course.getStartDate());

		courseRepository.save(existingCourse);
		return ResponseEntity.ok(new Response<>("success", existingCourse));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<?>> deleteCourseById(@PathVariable int id) {
		Course course = courseRepository.findById(id).orElse(null);
		if (course == null) {
			return new ResponseEntity<>(new Response<>("error", new ErrorMessage("Course not found")), HttpStatus.NOT_FOUND);
		}
		courseRepository.delete(course);
		return ResponseEntity.ok(new Response<>("success", course));
	}
}