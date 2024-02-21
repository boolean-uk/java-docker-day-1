package com.booleanuk.api.repository;

import com.booleanuk.api.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
