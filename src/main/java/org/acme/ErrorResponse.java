package org.acme;

import javax.annotation.Nullable;
import lombok.Data;

@Data
public class ErrorResponse {
  @Nullable private String message;
  @Nullable private String code;
}
