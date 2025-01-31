package com.booleanuk.api.student;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private String dob;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "start_date_course")
    private String startDateCourse;

    @Column(name = "average_grade")
    private double avgGrade;

    public Student(String firstName, String lastName, String dob, String courseTitle, String startDateCourse, double avgGrade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.courseTitle = courseTitle;
        this.startDateCourse = startDateCourse;
        this.avgGrade = avgGrade;
    }

    public Student(int id){
        this.id = id;
    }
}
