package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
@Table(name = "Registrations")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIncludeProperties(value = {"firstName", "lastName", "dob", "averageGrade"})
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIncludeProperties(value = {"title", "startDate"})
    private Course course;

    public Registration(int id){
        this.id = id;
    }

}
