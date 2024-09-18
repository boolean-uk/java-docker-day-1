package com.booleanuk.api.repository;

import com.booleanuk.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}