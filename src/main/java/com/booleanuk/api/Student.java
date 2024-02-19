package com.booleanuk.api;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String dob;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIncludeProperties(value = {"id","title", "description", "studyPoints", "startDate"})
    private Course course;

    @Column(name = "average_grade")
    private String averageGrade;

    public Student(String firstName, String lastName, String dob, String averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.averageGrade = averageGrade;
    }

    public Student(int id){
        this.id = id;
    }
}