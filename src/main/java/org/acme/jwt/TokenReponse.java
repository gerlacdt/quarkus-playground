package org.acme.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenReponse {

  @JsonProperty("access_token")
  public String accessToken;
}
