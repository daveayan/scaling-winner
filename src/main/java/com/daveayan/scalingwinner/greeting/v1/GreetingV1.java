package com.daveayan.scalingwinner.greeting.v1;

public class GreetingV1 {
    private long id;
    private final String content;

    public GreetingV1(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public GreetingV1(String content) {
        this.id = 0L;
        this.content = content;
    }

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getContent() {
        return this.content;
    }

    public String toString() {
        return "|== " + id + " == " + content + " ==|";
    }    
}
