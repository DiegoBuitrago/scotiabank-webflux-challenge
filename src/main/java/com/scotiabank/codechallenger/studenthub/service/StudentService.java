package com.scotiabank.codechallenger.studenthub.service;

import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

    Mono<StudentResponse> createStudent(StudentRequest request);

    Mono<StudentResponse> getStudentById(Long id);

    Flux<StudentResponse> getAllStudents();

    Mono<Void> deleteStudent(Long id);
}