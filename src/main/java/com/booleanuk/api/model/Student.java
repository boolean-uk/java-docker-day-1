package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties(value = {"students"})
    private Course course;

    @Column
    private String averageGrade;

    public Student(String fistName, String lastName, LocalDate dateOfBirth, String averageGrade) {
        this.firstName = fistName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.averageGrade = averageGrade;
    }
}
