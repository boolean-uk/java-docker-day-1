package com.booleanuk.api.repositories;

import com.booleanuk.api.models.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
}
