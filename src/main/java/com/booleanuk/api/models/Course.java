package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(nullable = false)
    private String id;
    private String title;
    private ZonedDateTime startDate;
    @ManyToMany
    private List<Module> modules;
    @OneToMany
    private List<Student> students;
}
