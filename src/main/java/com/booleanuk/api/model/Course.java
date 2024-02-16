package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "course_title")
    private String courseTitle;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private LocalDateTime courseStartDate;

    @OneToMany(mappedBy = "course")
    //For multible values to ignore use "value = {value,value}
    @JsonIncludeProperties(value = {"firstName","lastName"})
    private List<Student> students;

    public Course(String courseTitle, LocalDateTime courseStartDate) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
    }

    public Course(int id) {
        this.id = id;
    }
}
