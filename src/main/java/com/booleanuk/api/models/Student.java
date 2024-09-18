package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private double averageGrade;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("student")
    private List<StudentCourse> studentCourses;

    public Student(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(int id) {
        this.id = id;
    }

    public void calculateAndUpdateAverageGrade() {
        System.out.println("Calculating average grade for student " + this.firstName + " " + this.lastName + "..." + this);
        this.averageGrade = studentCourses.stream()
                .mapToDouble(StudentCourse::getGrade)
                .average()
                .orElse(0.0);
    }
}
