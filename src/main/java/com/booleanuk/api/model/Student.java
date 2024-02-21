package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
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
    private String dayOfBirth;

    @Column
    private String courseTitle;

    @Column
    private String startDayOfCourse;

    @Column
    private int averageGrade;

    public Student(String firstName, String lastName, String dayOfBirth, String courseTitle, String startDayOfCourse, int averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.courseTitle = courseTitle;
        this.startDayOfCourse = startDayOfCourse;
        this.averageGrade = averageGrade;
    }

}