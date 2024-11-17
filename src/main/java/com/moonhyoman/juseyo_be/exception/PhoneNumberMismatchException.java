package com.moonhyoman.juseyo_be.exception;

public class PhoneNumberMismatchException extends RuntimeException {
    public PhoneNumberMismatchException(String message) {
        super(message);
    }
}