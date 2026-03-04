package com.scotiabank.codechallenger.studenthub.exception;

public class StudentAlreadyExistsException extends RuntimeException {

    private final String code;

    public StudentAlreadyExistsException(Long id) {
        super("Student with ID " + id + " already exists");
        this.code = "STUDENT_ALREADY_EXISTS";
    }

    public String getCode() {
        return code;
    }
}