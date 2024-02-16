package com.booleanuk.api.student;

import com.booleanuk.api.course.Course;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String DateOfBirth;

    @Column
    private float avgGrade;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIncludeProperties(value = "title")
    private Course course;

    public Student(String firstName, String lastName, String dateOfBirth, float avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        DateOfBirth = dateOfBirth;
        this.avgGrade = avgGrade;
    }
}
