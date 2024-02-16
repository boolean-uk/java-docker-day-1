package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private LocalDateTime dateOfBirth;

    @Column(name = "course_title")
    private String courseTitle;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private LocalDateTime courseStartDate;

    @Column(name = "average_grade")
    private String averageGrade;

    /*
    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    @JsonIncludeProperties(value ={""})
    private Course course;

     */

    public Student(String firstName, String lastName, LocalDateTime dateOfBirth, String courseTitle, LocalDateTime courseStartDate, String averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.averageGrade = averageGrade;
    }
}
