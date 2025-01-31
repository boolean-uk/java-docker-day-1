package com.booleanuk.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String dateOfBirth;
    @Column
    private String courseTitle;
    @Column
    private String courseStartDate;
    @Column
    private double avgGrade;

    public Student(String firstName, String lastName, String dateOfBirth, String courseTitle, String courseStartDate,
            double avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.avgGrade = avgGrade;
    }
}
