package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courseStudentGrades")
public class CourseStudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course")
    private Course course;

    @Column
    private Integer grade;

    public CourseStudentGrade(Student student, Course course, Integer grade){
        this.student = student;
        this.course = course;
        this.grade = grade;
    }
}
