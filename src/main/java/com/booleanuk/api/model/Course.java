package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private long points;


    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties(value = {"courses"})
    private Set<Student> students;

    public Course(String name, long points) {
        this.name = name;
        this.points = points;
    }
}
