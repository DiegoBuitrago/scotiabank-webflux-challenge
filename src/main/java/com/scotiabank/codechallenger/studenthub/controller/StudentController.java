package com.scotiabank.codechallenger.studenthub.controller;

import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import com.scotiabank.codechallenger.studenthub.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createStudent(@Valid @RequestBody StudentRequest request) {
        return service.createStudent(request);
    }

    @GetMapping("/active")
    public Flux<Student> getActiveStudents() {
        return service.getActiveStudents();
    }

    @GetMapping
    public Flux<StudentResponse> getAllStudents() {
        return service.getAllStudents();
    }
}