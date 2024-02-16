package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private int birth;

    @Column
    private String course_title;

    @Column
    private int course_start;

    @Column
    private int average_grade;


    public Student(String first_name, String last_name, int birth,
                   String course_title, int course_start, int average_grade) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth = birth;
        this.course_title = course_title;
        this.course_start = course_start;
        this.average_grade = average_grade;
    }




}
