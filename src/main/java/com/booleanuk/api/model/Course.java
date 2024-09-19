package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data

@Entity
@Table(name="courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String name;

	// Many-to-Many relationship with Student
	@ManyToMany(mappedBy = "courses")
	@JsonIgnoreProperties("courses")
	private List<Student> students;
}
