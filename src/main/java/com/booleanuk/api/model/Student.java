package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String dob;

    @Column
    private double averageGrade;

    @ManyToMany
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("students")
    private List<Course> courses;

    public Student(int id) {
        this.id = id;
    }

    public void addCourse(Course c) {
        this.courses.add(c);
    }

}
