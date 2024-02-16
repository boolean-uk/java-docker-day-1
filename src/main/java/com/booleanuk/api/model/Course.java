package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate start;

    @Column
    private String grade;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Student> students;

    public Course(String title, LocalDate start, String grade) {
        this.title = title;
        this.start = start;
        this.grade = grade;
    }
}
