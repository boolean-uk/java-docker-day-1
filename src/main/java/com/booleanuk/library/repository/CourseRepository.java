package com.booleanuk.library.repository;

import com.booleanuk.library.models.Course;
import com.booleanuk.library.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
