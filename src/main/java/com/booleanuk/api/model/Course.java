package com.booleanuk.api.model;

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
    private String title;

    @Column
    private String description;

    @Column(name = "study_points")
    private int studyPoints;

    @Column(name = "start_date")
    private String startDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = {"id", "course"})
    public List<Student> students;

    public Course(String title, String description, int studyPoints, String startDate) {
        this.title = title;
        this.description = description;
        this.studyPoints = studyPoints;
        this.startDate = startDate;
    }
}
