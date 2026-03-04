package com.scotiabank.codechallenger.studenthub.service;

import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

    Mono<Void> createStudent(StudentRequest request);

    Flux<Student> getActiveStudents();

    Flux<StudentResponse> getAllStudents();
}