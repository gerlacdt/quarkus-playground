package org.acme;

import java.util.Objects;

public class HelloRequest {
  private String message;

  public HelloRequest() {}

  public HelloRequest(String msg) {
    this.message = msg;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HelloRequest that = (HelloRequest) o;
    return message.equals(that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }
}
