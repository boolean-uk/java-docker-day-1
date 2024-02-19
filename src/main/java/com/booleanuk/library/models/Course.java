package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "startDate")
    private String startDate;

    @OneToMany(mappedBy = "course")
    // @JsonIgnore
    private List<Student> students;

    public Course(String title, String startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course(String title, String startDate, List<Student> students) {
        this.title = title;
        this.startDate = startDate;
        this.students = students;
    }

    public Course(int id) {
        this.id = id;
    }
}
