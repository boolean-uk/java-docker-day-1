package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int totalPoints;

    @Column
    private String courseTitle;

    @Column
    private LocalDate courseStart;

    @ManyToMany(mappedBy = "courses")
    @JsonIncludeProperties(value = {"id"})
    private List<Student> students;

    public Course(int totalPoints, String courseTitle, LocalDate courseStart) {

        this.totalPoints = totalPoints;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
    }
}
