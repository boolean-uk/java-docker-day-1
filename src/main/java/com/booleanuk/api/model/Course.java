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
@Table(name = "courses")
public class Course {
  @Id
  private String code;

  @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
  @JsonIgnoreProperties("courses")
  private Set<Student> students;

  public void update(Course other) {
    this.students = other.students;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Course course)) return false;
    return Objects.equals(this.getCode(), course.getCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getCode());
  }

  @Override
  public String toString() {
    return "Course{" +
        "code='" + this.code + '\'' +
        '}';
  }
}