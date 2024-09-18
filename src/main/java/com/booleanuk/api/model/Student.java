package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(max = 30)
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @NotBlank
    @Size(max = 30)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @NotNull
    @Size(max = 1)
    @Column(name = "average_grade")
    private String averageGrade;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Course> courses;

    public Student(
            String firstName,
            String lastName,
            LocalDateTime birth,
            LocalDateTime startDate,
            String averageGrade)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = birth;
        this.startDate = startDate;
        this.averageGrade = averageGrade;
        this.courses = new ArrayList<>();
    }
}
