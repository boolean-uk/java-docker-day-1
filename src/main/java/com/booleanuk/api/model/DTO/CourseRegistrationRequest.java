package com.booleanuk.api.model.DTO;

import com.booleanuk.api.model.Course;
import com.booleanuk.api.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationRequest {
    private Student studentId;
    private Course courseId;
}
