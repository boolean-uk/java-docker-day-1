package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "courses")
@Getter
@Setter
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String courseTitle;

    @Column
    private String startDateForCourse;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students;

    public Course (int id) {
        this.id = id;
    }
}
