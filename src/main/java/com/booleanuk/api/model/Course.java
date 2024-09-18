package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseTitle;
    private String courseStartDate;
    private String avgGrade;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Student> students;


    public Course(String courseTitle, String courseStartDate, String avgGrade) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.avgGrade = avgGrade;
    }

    public Course(int id){
        this.id = id;
    }

}
