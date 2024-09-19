package com.booleanuk.api.dto;

import com.booleanuk.api.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    CourseDTO toDTO(Course course);
}
