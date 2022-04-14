package com.daveayan.scalingwinner.greeting.v2;

public class GreetingV2 {
    private long id;
    private final String message;
    private final String from;
    private final String to;

    public GreetingV2(long id, String message, String from, String to) {
        this.id = id;
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public GreetingV2(String message) {
        this.id = 0L;
        this.message = message;
        this.from = "";
        this.to = "";
    }

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMessage() {
        return this.message;
    }

    public String getFrom() {
        return this.from;
    }


    public String getTo() {
        return this.to;
    }

    public String toString() {
        return "|== " + id + " == " + message + " == " + from + " == " + to + " ==|";
    }    

}
