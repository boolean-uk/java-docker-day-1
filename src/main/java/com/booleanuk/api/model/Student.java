package com.booleanuk.api.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private String dateOfBrith;

    @Column
    private String courseTitle;

    @Column
    private String startDateOfCourse;

    @Column
    private long averageGrade;

    @ManyToMany
    @JoinTable(
            name = "course_like",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;


    public Student(String firstName, String lastName, String dateOfBrith, String courseTitle, String startDateOfCourse, long averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBrith = dateOfBrith;
        this.courseTitle = courseTitle;
        this.startDateOfCourse = startDateOfCourse;
        this.averageGrade = averageGrade;
    }



}
