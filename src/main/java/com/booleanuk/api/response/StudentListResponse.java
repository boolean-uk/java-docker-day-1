package com.booleanuk.api.response;



import com.booleanuk.api.student.Student;

import java.util.List;

public class StudentListResponse extends Response<List<Student>> {

    public StudentListResponse(List<Student> data) {
        super("success", data);
    }
}