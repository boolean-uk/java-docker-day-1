package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
    private int averageGrade;
    @ManyToMany
    @JoinColumn(name ="course_id", nullable = false)
    @JsonIncludeProperties(value = {"title"})
    @JsonIgnoreProperties("course")
    private List<Course> courses;

    public Student(String firstName, String lastName, String dayOfBirth, int averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.averageGrade = averageGrade;
    }

    public Student(int id) {
        this.id = id;
    }
}