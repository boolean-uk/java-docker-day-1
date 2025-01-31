package com.booleanuk.api.repository;

import com.booleanuk.api.model.CourseStudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStudentGradeRepository extends JpaRepository<CourseStudentGrade, Integer> {
}
