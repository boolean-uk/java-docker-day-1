package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    private String dateOfBirth;

    @Column(name = "average_grade")
    private double averageGrade;


    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "courseStart"})
    private Course course;

    public Student(String firstName, String lastName, String dateOfBirth, Course course, double averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
        this.averageGrade = averageGrade;
    }
}
