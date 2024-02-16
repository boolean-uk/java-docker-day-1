package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private OffsetDateTime start_date;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Module> modules;

}
