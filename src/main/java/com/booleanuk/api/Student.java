package com.booleanuk.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
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
    private String courseTitle;

    @Column
    private String courseStartDate;

    public Student(String firstName, String lastName, String dob, String courseTitle, String courseStartDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
    }

    public Student(int id){
        this.id = id;
    }
}
