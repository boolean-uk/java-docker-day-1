package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
    private String dateOfBirth;

    @Column
    private String courseTitle;

    @Column
    private String courseStartDate;

    @Column
    private String avgGrade;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Student(int id) {
        this.id = id;
    }

    public Student(String firstName, String lastName, String dateOfBirth, String courseTitle, String courseStartDate, String avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.avgGrade = avgGrade;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
