package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String DOB;
    @Column
    private String courseTitle;
    @Column
    private String courseStartDate;
    @Column
    private String avgGrade;

    @ManyToMany
    @JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    public Student(String firstName, String lastName, String DOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
    }

    public Student(int id) {
        this.id = id;
    }
}
