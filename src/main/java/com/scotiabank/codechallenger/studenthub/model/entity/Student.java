package com.scotiabank.codechallenger.studenthub.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("students")
public class Student {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private StudentStatus status;

    private Integer age;
}