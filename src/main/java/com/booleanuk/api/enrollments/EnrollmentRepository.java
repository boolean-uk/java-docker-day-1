package com.booleanuk.api.enrollments;

import com.booleanuk.api.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

List<Enrollment> findByStudent(Student student);
}
