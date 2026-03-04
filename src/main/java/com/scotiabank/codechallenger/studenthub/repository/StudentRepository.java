package com.scotiabank.codechallenger.studenthub.repository;

import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {

    // Método adicional para buscar por email
    Mono<Student> findByEmail(String email);
}