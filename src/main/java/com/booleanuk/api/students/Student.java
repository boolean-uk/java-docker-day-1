package com.booleanuk.api.students;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime dateOfBirth;

    @Column
    private String courseTitle;

    @Column
    private LocalDateTime courseStartDate;

    @Column
    private float averageGrade;

    public Student(String firstName, String lastName, LocalDateTime dateOfBirth, String courseTitle, LocalDateTime courseStartDate, float averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.averageGrade = averageGrade;
    }
}
