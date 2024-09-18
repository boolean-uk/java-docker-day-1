package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private String avgGrade;

    @ManyToOne
    @JoinColumn(name = "students")
    @JsonIgnoreProperties("students")
    private Course course;

    public Student(String firstName, String lastName, Date dob, String avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.course = null;
        this.avgGrade = avgGrade;
    }

    @JsonIgnore
    public void update (String firstName, String lastName, Date dob, Course course, String avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.course = course;
        this.avgGrade = avgGrade;
    }

    @JsonIgnore
    public boolean isInvalid() {
        return (StringUtils.isBlank(firstName)
                || StringUtils.isBlank(lastName)
                || dob == null
                || StringUtils.isBlank(avgGrade));
    }
}
