package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Data
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

    @ManyToMany
    @JoinTable(
            name = "Student_Course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Course> courses;

    @Column
    private double avgGrade;

    public Student(
            String firstName,
            String lastName,
            String dob,
            double avgGrade
    )   {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = LocalDate.parse(dob);
        this.avgGrade = avgGrade;
    }

    public Student(
            String firstName,
            String lastName,
            String dob,
            double avgGrade,
            List<Course> courses
    )   {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = LocalDate.parse(dob);
        this.avgGrade = avgGrade;
        this.courses = courses;
    }

    public Student(int id)  {
        this.id = id;
    }

    public void addCourse(Course course)    {
        this.courses.add(course);
    }
}
