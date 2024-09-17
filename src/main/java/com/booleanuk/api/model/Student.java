package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate DateOfBirth;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties(value = "students")
    private Course course;

    public Student(String firstName, String lastName, LocalDate dateOfBirth, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DateOfBirth = dateOfBirth;
        this.course = course;
    }

    public Student(int id) {
        this.id = id;
    }
}
