package org.acme.jwt;

import java.security.Principal;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/secured")
@RequestScoped
public class TokenSecuredResource {

  @Inject private JsonWebToken jwt;

  @Inject private TokenService tokenService;

  /**
   * Returns a valid JWT with hardcoded claims.
   *
   * @return a JWT
   * @throws Exception when parsing of private key fails
   */
  @GET()
  @Path("token")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public TokenReponse getToken() throws Exception {
    String token = this.tokenService.generateTokenString();
    TokenReponse response = new TokenReponse();
    response.setAccessToken(token);
    return response;
  }

  /**
   * Returns infos about the SecurityContext. Everyone is authorized.
   *
   * @param ctx the securityContext
   * @return securityContext infos
   */
  @GET()
  @Path("permit-all")
  @PermitAll
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@Context SecurityContext ctx) {
    Principal caller = ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    boolean hasJwt = jwt.getClaimNames() != null;
    String helloReply =
        String.format(
            "hello + %s, isSecure: %s, authScheme: %s, hasJwt: %s",
            name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt);
    return helloReply;
  }

  /**
   * Returns infos about the SecurityContext. Only authenicated users with valid JWTs are allowed.
   *
   * @param ctx the securityContext
   * @return securityContext infos
   */
  @GET()
  @Path("roles-allowed")
  @RolesAllowed({"Echoer", "Subscriber"})
  @Produces(MediaType.TEXT_PLAIN)
  public String helloRolesAllowed(@Context SecurityContext ctx) {
    Principal caller = ctx.getUserPrincipal();
    String name = caller == null ? "anonymous" : caller.getName();
    boolean hasJwt = jwt.getClaimNames() != null;
    String helloReply =
        String.format(
            "hello + %s, isSecure: %s, authScheme: %s, hasJwt: %s",
            name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt);
    return helloReply;
  }
}
