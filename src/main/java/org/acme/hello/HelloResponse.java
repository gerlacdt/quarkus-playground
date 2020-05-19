package org.acme.hello;

import java.util.Objects;

public class HelloResponse {
  private String message;

  public HelloResponse() {}

  public HelloResponse(String msg) {
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
    HelloResponse that = (HelloResponse) o;
    return message.equals(that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }
}
