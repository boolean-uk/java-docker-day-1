package com.booleanuk.api.library.repositories;

import com.booleanuk.api.library.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
