package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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
    private LocalDate dateOfBirth;

    @Column
    private String courseTitle;

    @Column
    private LocalDate courseStartDate;

    @Column
    private String averageGrade;

    public Student(String fistName, String lastName, LocalDate dateOfBirth, String courseTitle,
                   LocalDate courseStartDate, String averageGrade) {
        this.firstName = fistName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.averageGrade = averageGrade;
    }
}
