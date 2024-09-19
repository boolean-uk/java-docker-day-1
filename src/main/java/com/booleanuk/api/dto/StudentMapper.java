package com.booleanuk.api.dto;

import com.booleanuk.api.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    StudentDTO toDTO(Student student);
}
