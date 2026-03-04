package com.scotiabank.codechallenger.studenthub.controller;

import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
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

    // Crear estudiante
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        return service.createStudent(request);
    }

    // Obtener estudiante por ID
    @GetMapping("/{id}")
    public Mono<StudentResponse> getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    // Obtener todos los estudiantes
    @GetMapping
    public Flux<StudentResponse> getAllStudents() {
        return service.getAllStudents();
    }

    // Eliminar estudiante
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteStudent(@PathVariable Long id) {
        return service.deleteStudent(id);
    }
}