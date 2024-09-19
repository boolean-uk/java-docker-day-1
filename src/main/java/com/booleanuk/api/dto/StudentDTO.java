package com.booleanuk.api.dto;

import com.booleanuk.api.model.Course;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {
    private int id;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "dateOfBirth is required")
    private String dateOfBirth;

    private List<Course> courses;
}

