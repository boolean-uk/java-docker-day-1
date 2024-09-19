package com.booleanuk.api.enrollments;

import com.booleanuk.api.courses.Course;
import com.booleanuk.api.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Enrollment(int id) {
        this.id = id;
    }
}
