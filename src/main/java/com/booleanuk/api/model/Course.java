package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "start_date")
    private String startDate;
    @OneToMany(mappedBy = "course")
    @JsonIncludeProperties(value = {"firstName", "lastName", "dateOfBirth", "averageGrade"})
    private List<Student> students;
    public Course(String title, String startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course(int id) {
        this.id = id;
    }
}

