package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String dob;

    @Column
    private String courseName;

    @Column
    private String startDate;

    @Column
    private String avgGrade;

    @ManyToOne
    @JsonIgnoreProperties(value ={"students, courses"})
    @JoinColumn(name="courseId")
    private Course course;

    Student(String firstName, String lastName, String dob, String courseName, String startDate, String avgGrade){
        this.firstName=firstName;
        this.lastName=lastName;
        this.dob=dob;
        this.courseName=courseName;
        this.startDate=startDate;
        this.avgGrade=avgGrade;
    }

}
