package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @Column
    private String title;

    @Column
    private String startDate;


    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = "course", allowSetters = true)
    private List<Student> students;

    public Course(String title, String startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course(int id) {
        this.id = id;
    }
}
