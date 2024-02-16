package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String DOB;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private Course course;

    public Student(String first_name, String last_name, String DOB) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
    }
}
