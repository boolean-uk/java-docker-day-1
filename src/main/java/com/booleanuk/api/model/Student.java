package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Student(Integer id) {
		this.id = id;
	}

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private OffsetDateTime dateOfBirth;

	@Column(nullable = false)
	private String courseTitle;

	@Column(nullable = false)
	private OffsetDateTime startDateforCourse;

	@Column(nullable = false)
	private Float averageGrade;

	// add course connection
	@ManyToMany
	@JoinTable(
			name = "student_courses", // The join table
			joinColumns = @JoinColumn(name = "student_id"), // Foreign key for Student
			inverseJoinColumns = @JoinColumn(name = "course_id") // Foreign key for Course
	)
	@JsonIgnoreProperties("students") // Avoid circular references
	private List<Course> courses;
}
