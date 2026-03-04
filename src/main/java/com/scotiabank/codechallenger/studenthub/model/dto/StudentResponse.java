package com.scotiabank.codechallenger.studenthub.model.dto;

import lombok.*;

import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private StudentStatus status;
    private Integer age;
}