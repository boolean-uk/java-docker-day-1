package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String title;
	@Column
	private Date startDate;
	@ManyToMany(mappedBy = "courses")
	private Set<Student> students = new HashSet<>();

	public Course(String title, Date startDate) {
		this.title = title;
		this.startDate = startDate;
	}

	public Course(int id) {
		this.id = id;
	}
}
