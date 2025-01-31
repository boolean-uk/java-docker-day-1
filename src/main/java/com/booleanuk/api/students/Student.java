package com.booleanuk.api.students;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String dob;

    @Column
    private String course_title;

    @Column
    private String start_date;

    @Column
    private String average_grade;

    public Student(int id) {
        this.id = id;
    }
}