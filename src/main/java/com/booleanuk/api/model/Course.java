package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String startDate;
    @Column
    private String avgGrade;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    public Course(String name, String startDate, String avgGrade) {
        this.name = name;
        this.startDate = startDate;
        this.avgGrade = avgGrade;
    }

    public Course(int id) {
        this.id = id;
    }
}
