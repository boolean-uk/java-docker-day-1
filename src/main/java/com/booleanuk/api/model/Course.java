package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private LocalDate startDate;

    @Column
    private char avgGrade;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    public Course(String title, LocalDate startDate, char avgGrade) {
        this.title = title;
        this.startDate = startDate;
        this.avgGrade = avgGrade;
    }
}
