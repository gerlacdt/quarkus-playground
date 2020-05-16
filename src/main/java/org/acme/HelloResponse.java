package org.acme;

public class HelloResponse {

    private String message;

    public HelloResponse(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
