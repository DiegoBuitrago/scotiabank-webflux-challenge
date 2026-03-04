package com.scotiabank.codechallenger.studenthub.service.impl;

import com.scotiabank.codechallenger.studenthub.mapper.StudentMapper;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.repository.StudentRepository;
import com.scotiabank.codechallenger.studenthub.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Mono<StudentResponse> createStudent(StudentRequest request) {
        return repository.findByEmail(request.getEmail())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Email already exists"));
                    }
                    return repository.save(StudentMapper.toEntity(request))
                            .map(StudentMapper::toResponse);
                });
    }

    @Override
    public Mono<StudentResponse> getStudentById(Long id) {
        return repository.findById(id)
                .map(StudentMapper::toResponse);
    }

    @Override
    public Flux<StudentResponse> getAllStudents() {
        return repository.findAll()
                .map(StudentMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteStudent(Long id) {
        return repository.deleteById(id);
    }
}