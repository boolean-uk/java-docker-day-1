package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
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
    private int averageGrade;

    @ManyToOne
    @JoinColumn(name ="course_id", nullable = false)
    @JsonIncludeProperties(value = {"title"})
    @JsonIgnoreProperties("course")
    private Course course;

    public Student(String firstName, String lastName, String dateOfBirth, int averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.averageGrade = averageGrade;
    }

    public Student(int id) {
        this.id = id;
    }
}
