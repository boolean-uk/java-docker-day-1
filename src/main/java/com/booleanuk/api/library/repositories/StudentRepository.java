package com.booleanuk.api.library.repositories;

import com.booleanuk.api.library.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
