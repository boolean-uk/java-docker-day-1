package com.booleanuk.api.student;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    private String dateOfBirth;

    @Column
    private int averageGrade;

    public Student(String firstName, String lastName, String dateOfBirth, int averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.averageGrade = averageGrade;
    }

    public Student(int id) {
        this.id = id;
    }
}
