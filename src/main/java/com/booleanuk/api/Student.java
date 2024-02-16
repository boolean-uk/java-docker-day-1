package com.booleanuk.api;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonIncludeProperties(value = "title")
    private Course course;

    public Student(String firstName, String lastName, String dob, String courseTitle, String courseStartDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public Student(int id){
        this.id = id;
    }
}
