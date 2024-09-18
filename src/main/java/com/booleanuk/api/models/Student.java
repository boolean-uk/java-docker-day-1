package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Students")
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String dateOfBirth;  // Changed to String

    @Column(nullable = false)
    private String courseTitle;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private double averageGrade;

    public Student() {}

    public Student(String firstName, String lastName, String dateOfBirth, String courseTitle, String startDate, double averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.averageGrade = averageGrade;
    }
}
