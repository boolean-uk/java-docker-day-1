package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
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

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "start_date_course")
    private String startDateCourse;

    @Column(name = "average_grade")
    private int averageGrade;

    public Student(String firstName, String lastName, String dateOfBirth, String courseTitle,
                   String startDateCourse, int averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.startDateCourse = startDateCourse;
        this.averageGrade = averageGrade;
    }
}
