package com.accenture.business.exception;

public class MustNotNullException extends RuntimeException{

    public MustNotNullException() {
        super();
    }

    public MustNotNullException(String s) {
        super(s);
    }
}
