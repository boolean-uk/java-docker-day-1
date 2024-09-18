package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String title;

    @Column
    private String teacher;

    @Column
    private String startDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value ={"courses", "students"})
    private List<Student> students;

    public Course(String title, String teacher, String startDate){
        this.title=title;
        this.teacher=teacher;
        this.startDate=startDate;
    }

}
