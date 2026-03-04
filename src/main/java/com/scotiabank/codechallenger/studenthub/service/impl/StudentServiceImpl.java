package com.scotiabank.codechallenger.studenthub.service.impl;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.scotiabank.codechallenger.studenthub.exception.StudentAlreadyExistsException;
import com.scotiabank.codechallenger.studenthub.mapper.StudentMapper;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;
import com.scotiabank.codechallenger.studenthub.repository.StudentRepository;
import com.scotiabank.codechallenger.studenthub.service.StudentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Void> createStudent(StudentRequest request) {
        return repository.existsById(request.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new StudentAlreadyExistsException(request.getId()));
                    }
                    Student student = Student.builder()
                            .id(request.getId())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .status(request.getStatus())
                            .age(request.getAge())
                            .build();
                    return template.insert(Student.class)
                            .using(student)
                            .then();
                });
    }

    @Override
    public Flux<Student> getActiveStudents() {
        return repository.findByStatus(StudentStatus.ACTIVE);
    }

    @Override
    public Flux<StudentResponse> getAllStudents() {
        return repository.findAll().map(StudentMapper::toResponse);
    }
}