package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private OffsetDateTime startDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Student> students;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private OffsetDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Course(String title, OffsetDateTime startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course(int id) {
        this.id = id;
    }

    public void setStartsAt(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        ZonedDateTime zonedDateTime = OffsetDateTime.parse(startDate, formatter).atZoneSameInstant(ZoneId.systemDefault());
        this.startDate = zonedDateTime.toOffsetDateTime();
    }
}

