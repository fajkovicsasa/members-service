package com.example.memberservice.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("Member with id " + id + " does not exist.");
    }
}
