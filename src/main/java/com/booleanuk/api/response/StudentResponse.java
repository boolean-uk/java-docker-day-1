package com.booleanuk.api.response;

import com.booleanuk.api.student.Student;


public class StudentResponse extends Response<Student>{

    public StudentResponse(Student student) {
        super("success", student);
    }
}

