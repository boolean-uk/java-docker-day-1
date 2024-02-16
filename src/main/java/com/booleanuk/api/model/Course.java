package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private LocalDate start;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Student_Course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private List<Student> students;

    public Course(
            String title,
            String start
    )   {
        this.title = title;
        this.start = LocalDate.parse(start);
    }

    public Course(int id)   {
        this.id = id;
    }

    public void addStudent(Student student)  {
        this.students.add(student);
    }
}
