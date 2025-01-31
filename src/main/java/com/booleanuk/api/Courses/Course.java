package com.booleanuk.api.Courses;

import com.booleanuk.api.Responses.StudentResponse;
import com.booleanuk.api.Students.Student;
import com.booleanuk.api.studentCourses.StudentCourse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Nullability;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@JsonIgnoreProperties({"studentCourses"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private LocalDate startDateForCourse;

    @Column double averageGrade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<StudentCourse> studentCourses;

    public Course(String name, LocalDate startDateForCourse, double averageGrade){
        this.name = name;
        this.startDateForCourse = startDateForCourse;
        this.averageGrade = averageGrade;
    }

    public Course(int id){
        this.id = id;
    }
}
