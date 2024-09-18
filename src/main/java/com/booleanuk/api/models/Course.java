package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String startDate;
    private String endDate;

    public Course(String title, String description, String startDate, String endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("course")
    private List<StudentCourse> studentCourses;
}