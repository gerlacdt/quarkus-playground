package org.acme;

public class HelloRequest {

    private String message;

    public HelloRequest() {

    }

    public HelloRequest(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
