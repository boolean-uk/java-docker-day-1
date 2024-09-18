package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date courseStartDate;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    public Course (String title, Date courseStartDate) {
        this.title = title;
        this.courseStartDate = courseStartDate;
        this.students = new ArrayList<>();
    }



    @JsonIgnore
    public void update (String title, Date courseStartDate) {
        this.title = title;
        this.courseStartDate = courseStartDate;
    }

    @JsonIgnore
    public boolean isInvalid() {
        return (StringUtils.isBlank(title)
                || courseStartDate == null);
    }
}
