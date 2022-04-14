package com.daveayan.scalingwinner.greeting.common;

public class GreetingNotFoundException extends Exception {
    String message;
    public GreetingNotFoundException(Long id) { 
        super();
        this.message = "Record not found - " + id;
    }
}
