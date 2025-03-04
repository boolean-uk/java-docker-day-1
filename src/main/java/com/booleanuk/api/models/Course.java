package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "credits")
    private int credits;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIncludeProperties(value = {"firstName", "lastName"})
    private Student student;

    public Course(String title, int credits, String startDate, String endDate) {
        this.title = title;
        this.credits = credits;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
