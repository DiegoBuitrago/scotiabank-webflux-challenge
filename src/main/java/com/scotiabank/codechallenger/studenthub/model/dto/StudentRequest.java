package com.scotiabank.codechallenger.studenthub.model.dto;

import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private StudentStatus status;

    @Min(1)
    @Max(120)
    private Integer age;
}