package com.booleanuk.api.repository;

import com.booleanuk.api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StudentRepository extends JpaRepository<Student, Integer> {
}
