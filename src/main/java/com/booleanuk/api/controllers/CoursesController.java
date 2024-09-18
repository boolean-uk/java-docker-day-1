package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Course;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CoursesController extends GenericController<Course, Integer> {


}
