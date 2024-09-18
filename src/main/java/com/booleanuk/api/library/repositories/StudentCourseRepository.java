package com.booleanuk.api.library.repositories;

import com.booleanuk.api.library.models.Course;
import com.booleanuk.api.library.models.StudentCourse;
import com.booleanuk.api.library.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    List<StudentCourse> findByCourseAndStudent(Course course, Student student);

    StudentCourse findByIdAndCourseAndStudent(int id, Course course, Student student);

    List<StudentCourse> findByStudent(Student student);
}
