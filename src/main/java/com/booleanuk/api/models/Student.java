package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "startDate" })
    private Course course;

    @Override
    public boolean haveNullFields() {
        return firstName == null || lastName == null || dateOfBirth == null;
    }

    @Override
    public void copyOverData(Model model) {
        Student _other = (Student) model;

        firstName = _other.firstName;
        lastName = _other.lastName;
        dateOfBirth = _other.dateOfBirth;
        course = _other.course;
    }
}
