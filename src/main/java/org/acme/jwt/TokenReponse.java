package org.acme.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.Data;

@Data
public class TokenReponse {

  @Nullable
  @JsonProperty("access_token")
  public String accessToken;
}
