package com.booleanuk.api.studentCourses;

import com.booleanuk.api.Courses.Course;
import com.booleanuk.api.Students.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "studentCourses")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="student")
    private Student student;

    @ManyToOne
    @JoinColumn(name="course")
    private Course course;


    public StudentCourse(Student student, Course course) {
        this.course = course;
        this.student = student;
    }

    public StudentCourse(int id) {
        this.id = id;
    }

}
