package com.booleanuk.api.dto;

import com.booleanuk.api.model.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseDTO {
    private int id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "startDate is required")
    private String startDate;

    @NotNull(message = "averageGrade is required")
    private char averageGrade;

    Set<Student> students;
}
