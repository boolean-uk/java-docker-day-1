package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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
    private Integer id;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "startDate")
    private String startDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }
}
