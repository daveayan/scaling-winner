package com.daveayan.scalingwinner.greeting.common;

public class DuplicateGreetingException extends Exception {
    String message;
    public DuplicateGreetingException(Long id) { 
        super();
        this.message = "The record already exists - " + id;
    }
}
