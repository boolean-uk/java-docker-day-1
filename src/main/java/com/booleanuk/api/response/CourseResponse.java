package com.booleanuk.api.response;


import com.booleanuk.api.course.Course;

public class CourseResponse extends Response<Course>{

    public CourseResponse(Course course) {
        super("success", course);
    }
}