package com.scotiabank.codechallenger.studenthub.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import com.scotiabank.codechallenger.studenthub.exception.StudentAlreadyExistsException;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentRequest;
import com.scotiabank.codechallenger.studenthub.model.dto.StudentResponse;
import com.scotiabank.codechallenger.studenthub.model.entity.Student;
import com.scotiabank.codechallenger.studenthub.model.entity.StudentStatus;
import com.scotiabank.codechallenger.studenthub.repository.StudentRepository;
import com.scotiabank.codechallenger.studenthub.service.impl.StudentServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StudentServiceImplTest {
    @Mock
    private StudentRepository repository;

    @Mock
    private R2dbcEntityTemplate template;

    private StudentServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new StudentServiceImpl(repository, template);
    }

    @Test
    public void shouldCreateStudentSuccessfully() {

        StudentRequest request = StudentRequest.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(repository.existsById(1L)).thenReturn(Mono.just(false));

        when(template.insert(Student.class))
                .thenReturn(mock(org.springframework.data.r2dbc.core.ReactiveInsertOperation.ReactiveInsert.class));

        // Simulamos insert().using(student).then()
        var insertMock = mock(org.springframework.data.r2dbc.core.ReactiveInsertOperation.ReactiveInsert.class);
        when(template.insert(Student.class)).thenReturn(insertMock);
        when(insertMock.using(any(Student.class))).thenReturn(Mono.just(new Student()));

        Mono<Void> result = service.createStudent(request);

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).existsById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenStudentAlreadyExists() {

        StudentRequest request = StudentRequest.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(repository.existsById(1L)).thenReturn(Mono.just(true));

        Mono<Void> result = service.createStudent(request);

        StepVerifier.create(result)
                .expectError(StudentAlreadyExistsException.class)
                .verify();

        verify(repository).existsById(1L);
        verify(template, never()).insert(any());
    }

    @Test
    public void shouldReturnActiveStudents() {

        Student student = Student.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(repository.findByStatus(StudentStatus.ACTIVE))
                .thenReturn(Flux.just(student));

        Flux<Student> result = service.getActiveStudents();

        StepVerifier.create(result)
                .expectNext(student)
                .verifyComplete();

        verify(repository).findByStatus(StudentStatus.ACTIVE);
    }

    @Test
    public void shouldReturnAllStudents() {

        Student student = Student.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Buitrago")
                .status(StudentStatus.ACTIVE)
                .age(25)
                .build();

        when(repository.findAll()).thenReturn(Flux.just(student));

        Flux<StudentResponse> result = service.getAllStudents();

        StepVerifier.create(result)
                .assertNext(response -> {
                    assert response.getId().equals(1L);
                    assert response.getFirstName().equals("Diego");
                })
                .verifyComplete();

        verify(repository).findAll();
    }
}