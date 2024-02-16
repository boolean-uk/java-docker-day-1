package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
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

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIncludeProperties(value = {"id","title", "description", "studyPoints", "startDate"})
    private Course course;

    @Column(name = "average_grade")
    private String averageGrade;

    private Student(int id) {
        this.id = id;
    }

    public Student(String firstName, String lastName, String dateOfBirth, Course course, String averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
        this.averageGrade = averageGrade;
    }
}
