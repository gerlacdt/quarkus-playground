package org.acme.hello;

import java.util.Objects;
import javax.annotation.Nullable;

public class HelloRequest {

  @Nullable private String message;

  public HelloRequest() {}

  public HelloRequest(String msg) {
    this.message = msg;
  }

  public @Nullable String getMessage() {
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
    if (message != null) {
      return message.equals(that.message);
    }
    return message == that.message;
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }
}
