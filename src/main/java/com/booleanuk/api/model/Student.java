package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
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

    @Column
    private String grade;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title", "startDate"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Student(String firstName, String lastName, String dob, String grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.grade = grade;
    }
}
