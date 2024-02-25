package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String startDateOfCourse;


    @OneToMany(mappedBy = "courses")
    @JsonIgnoreProperties(value = {"courses"}, allowSetters = true)
    private List<Student> students;

    public Course(String title, String startDateOfCourse) {
        this.title = title;
        this.startDateOfCourse = startDateOfCourse;
    }

    public Course(int id) {
        this.id = id;
    }

}
