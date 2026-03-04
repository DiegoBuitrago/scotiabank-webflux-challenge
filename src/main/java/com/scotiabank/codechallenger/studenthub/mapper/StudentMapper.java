package com.scotiabank.codechallenger.studenthub.mapper;

import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;

public class StudentMapper {

    // Convierte request en entidad
    public static Student toEntity(StudentRequest request) {
        return Student.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(request.getStatus())
                .age(request.getAge())
                .build();
    }

    // Convierte entidad en response
    public static StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .age(student.getAge())
                .build();
    }
}