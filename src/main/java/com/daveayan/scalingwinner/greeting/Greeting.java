package com.daveayan.scalingwinner.greeting;

public class Greeting {
    private long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Greeting(String content) {
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
