package com.booleanuk.api.course;

import com.booleanuk.api.student.Student;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    private LocalDateTime startDate;

    @OneToMany(mappedBy = "course")
    @JsonIncludeProperties(value = {"firstName", "lastName"})
    private List<Student> students;

    public Course(String title, LocalDateTime startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course(int id) {
        this.id = id;
    }


}
