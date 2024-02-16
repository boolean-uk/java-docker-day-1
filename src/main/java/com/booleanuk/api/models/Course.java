package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String course_title;
    @Column
    private String start_date;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Student> students;

    public Course(String course_title, String start_date) {
        this.course_title = course_title;
        this.start_date = start_date;
    }
}
