package com.booleanuk.api.model;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIncludeProperties(value = {"id","firstName", "lastName","email"})
    private Student student;

    public Course(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}
