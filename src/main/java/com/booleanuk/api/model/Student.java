package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String birthday;

  @Column(nullable = false)
  private String courseStartDate;

  @Column(nullable = false)
  private double averageGrade;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinTable(
      name = "student_courses",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id")
  )
  @JsonIgnoreProperties("students")
  private Set<Course> courses;

  public void update(Student other) {
    this.firstName = other.firstName;
    this.lastName = other.lastName;
    this.birthday = other.birthday;
    this.courseStartDate = other.courseStartDate;
    this.averageGrade = other.averageGrade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Student student)) return false;
    return this.getId() == student.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + this.id +
        ", firstName='" + this.firstName + '\'' +
        ", lastName='" + this.lastName + '\'' +
        ", birthday='" + this.birthday + '\'' +
        ", courseStartDate='" + this.courseStartDate + '\'' +
        ", averageGrade=" + this.averageGrade +
        '}';
  }
}