package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "courseTitle")
    private String courseTitle;

    @Column(name = "startDateForCourse")
    private LocalDate startDateForCourse;

    @Column(name = "averageGrade")
    private double averageGrade;


    public Student(String firstName, String lastName, String dateOfBirth, String courseTitle, LocalDate startDateForCourse, double averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.startDateForCourse = startDateForCourse;
        this.averageGrade = averageGrade;
    }


    public Student(int id) {
        this.id = id;
    }

}
