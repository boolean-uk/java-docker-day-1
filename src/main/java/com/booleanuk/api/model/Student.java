package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Date dateOfBirth;
	@Column
	private String courseTitle;
	@Column
	private Date startDateForCourse;
	@Column
	private double averageGrade;

	@ManyToMany
	@JoinTable(
			name = "student_course",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id")
	)
	private Set<Course> courses = new HashSet<>();

	public Student(int id) {
		this.id = id;
	}

	public Student(String firstName, String lastName, Date dateOfBirth, String courseTitle, Date startDateForCourse, double averageGrade) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.courseTitle = courseTitle;
		this.startDateForCourse = startDateForCourse;
		this.averageGrade = averageGrade;
	}
}

