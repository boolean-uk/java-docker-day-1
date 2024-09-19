package com.booleanuk.api.courses;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String courseName;

    @Column
    private String startDate;

    public Course(String courseName, String startDate) {
        this.courseName = courseName;
        this.startDate = startDate;
    }

    public Course(int id) {
        this.id = id;
    }
}
