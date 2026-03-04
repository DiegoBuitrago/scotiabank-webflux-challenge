package com.scotiabank.codechallenger.studenthub.repository;

import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {

    // Método adicional para buscar por email
    Flux<Student> findByStatus(StudentStatus status);
}