package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Size(max = 150)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Size(max = 30)

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotBlank
    @Size(max = 30)
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @Size(max = 50)
    @Column(name = "credits")
    private int credits;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    public Course(String title, String description, LocalDateTime startDate, LocalDateTime endDate, int credits) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.credits = credits;
    }
}
