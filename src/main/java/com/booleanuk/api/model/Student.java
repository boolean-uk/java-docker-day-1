package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name = "firstName")
    private String firstName;

    @Column( name = "lastName")
    private String lastName;

    @Column
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "coure_id", nullable = false)
    @JsonIncludeProperties(value = {"title"})
    private Course course;

    public Student(String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
}
