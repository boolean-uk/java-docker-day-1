package com.booleanuk.api.Students;

import com.booleanuk.api.Courses.Course;
import com.booleanuk.api.studentCourses.StudentCourse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"studentCourses"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDate dob;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<StudentCourse> studentCourses;

    public Student(String firstName, String lastName, LocalDate dob){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Student(int id){
        this.id = id;
    }
}
