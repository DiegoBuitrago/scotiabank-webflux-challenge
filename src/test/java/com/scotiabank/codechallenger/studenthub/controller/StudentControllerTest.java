package com.scotiabank.codechallenger.studenthub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.scotiabank.codechallenger.studenthub.exception.GlobalExceptionHandler;
import com.scotiabank.codechallenger.studenthub.exception.StudentAlreadyExistsException;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;
import com.scotiabank.codechallenger.studenthub.service.StudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = StudentController.class)
@Import(GlobalExceptionHandler.class)
public class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StudentService service;

    // =========================================
    // POST /api/students - SUCCESS
    // =========================================

    @Test
    void shouldCreateStudentSuccessfully() {

        StudentRequest request = StudentRequest.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(service.createStudent(any(StudentRequest.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/students")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        verify(service, times(1)).createStudent(any(StudentRequest.class));
    }

    // =========================================
    // POST /api/students - STUDENT EXISTS
    // =========================================

    @Test
    void shouldReturnConflictWhenStudentAlreadyExists() {

        StudentRequest request = StudentRequest.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(service.createStudent(any(StudentRequest.class)))
                .thenReturn(Mono.error(new StudentAlreadyExistsException(1L)));

        webTestClient.post()
                .uri("/api/students")
                .bodyValue(request)
                .exchange()
//                .expectStatus().isConflict()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.code").isEqualTo("STUDENT_ALREADY_EXISTS")
                .jsonPath("$.message").isEqualTo("Student with ID 1 already exists")
                .jsonPath("$.timestamp").exists();

        verify(service, times(1)).createStudent(any(StudentRequest.class));
    }

    // =========================================
    // POST /api/students - VALIDATION ERROR
    // =========================================

    @Test
    void shouldReturnBadRequestWhenValidationFails() {

        StudentRequest invalidRequest = StudentRequest.builder()
                .id(null)
                .firstName("")
                .lastName("")
                .status(null)
                .age(200)
                .build();

        webTestClient.post()
                .uri("/api/students")
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // =========================================
    // GET /api/students/active
    // =========================================

    @Test
    void shouldReturnActiveStudents() {

        Student student = Student.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(service.getActiveStudents())
                .thenReturn(Flux.just(student));

        webTestClient.get()
                .uri("/api/students/active")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Student.class)
                .hasSize(1);

        verify(service, times(1)).getActiveStudents();
    }

    // =========================================
    // GET /api/students
    // =========================================

    @Test
    void shouldReturnAllStudents() {

        StudentResponse response = StudentResponse.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(service.getAllStudents())
                .thenReturn(Flux.just(response));

        webTestClient.get()
                .uri("/api/students")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentResponse.class)
                .hasSize(1);

        verify(service, times(1)).getAllStudents();
    }
}
