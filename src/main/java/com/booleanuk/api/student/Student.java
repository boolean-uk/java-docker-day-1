package com.booleanuk.api.student;

import com.booleanuk.api.course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private String DateOfBirth;

    @Column
    private String Title;

    @Column
    private LocalDateTime startDateCourse;

    @Column
    private float avgGrade;

//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = false)
//    private Course course;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
