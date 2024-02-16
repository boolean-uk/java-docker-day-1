package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDate dob;

    @Column
    private String courseTitle;

    @Column
    private LocalDate courseStart;

    @Column
    private double avgGrade;

    public Student(
            String firstName,
            String lastName,
            String dob,
            String courseTitle,
            String courseStart,
            double avgGrade
    )   {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = LocalDate.parse(dob);
        this.courseTitle = courseTitle;
        this.courseStart = LocalDate.parse(courseStart);
        this.avgGrade = avgGrade;
    }
}
