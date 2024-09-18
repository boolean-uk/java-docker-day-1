package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @ManyToOne
    @JoinColumn(name= "course_id", nullable = false)
    // @JsonIgnore
    private Course course;

    public Student(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(String firstName, String lastName, String dateOfBirth, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
    }

    public Student(int id) {
        this.id = id;
    }
}
