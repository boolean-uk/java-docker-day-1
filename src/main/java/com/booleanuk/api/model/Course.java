package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = "course")
    private List<Student> students = new ArrayList<>();

    public Course(String name, LocalDate startDate, List<Student> students) {
        this.name = name;
        this.startDate = startDate;
        this.students = students;
    }

    public Course(int id) {
        this.id = id;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
