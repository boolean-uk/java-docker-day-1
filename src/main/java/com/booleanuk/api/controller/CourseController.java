package com.booleanuk.api.controller;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.view.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

	private final CourseRepository courseRepository;

	public CourseController(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	// GET: Retrieve all courses
	@GetMapping
	public ResponseEntity<List<Course>> getAll() {
		List<Course> courses = courseRepository.findAll();
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	// GET {id}: Retrieve a course by ID
	@GetMapping("{id}")
	public ResponseEntity<Course> getOne(@PathVariable Integer id) {
		return courseRepository.findById(id)
				.map(course -> new ResponseEntity<>(course, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// POST: Create a new course
	@PostMapping
	public ResponseEntity<Course> postOne(@RequestBody Course course) {
		if (isValidCourse(course)) {
			Course createdCourse = courseRepository.save(course);
			return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// PUT {id}: Update a course by ID
	@PutMapping("{id}")
	public ResponseEntity<Course> putOne(@PathVariable Integer id, @RequestBody Course updatedCourse) {
		return courseRepository.findById(id)
				.map(existingCourse -> {
					updateCourseDetails(existingCourse, updatedCourse); // Helper method to update course fields
					courseRepository.save(existingCourse);
					return new ResponseEntity<>(existingCourse, HttpStatus.OK);
				})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// DELETE {id}: Delete a course by ID
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
		return courseRepository.findById(id)
				.map(course -> {
					courseRepository.delete(course);
					return new ResponseEntity<>(HttpStatus.OK);
				})
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Helper method to check if the course is valid (you can add your own validation rules)
	private boolean isValidCourse(Course course) {
		return course.getName() != null && !course.getName().isEmpty();
	}

	// Helper method to update course details
	private void updateCourseDetails(Course existingCourse, Course updatedCourse) {
		existingCourse.setName(updatedCourse.getName());
	}
}
