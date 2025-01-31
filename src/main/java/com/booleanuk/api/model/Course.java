package com.booleanuk.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Table(name = "courses")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title") private String title;
    @Column(name = "start_date") private Date startDate;

    @ManyToMany(mappedBy = "myCourses")
    Set<Student> myStudents;

    public Course(String title, Date startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
