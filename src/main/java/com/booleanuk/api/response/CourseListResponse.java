package com.booleanuk.api.response;


import com.booleanuk.api.course.Course;

import java.util.List;

public class CourseListResponse extends Response<List<Course>> {

    public CourseListResponse(List<Course> data) {
        super("success", data);
    }
}