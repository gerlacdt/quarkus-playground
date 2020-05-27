package org.acme.jwt;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

@ApplicationScoped
public class TokenService {

  private PrivateKey privateKey;

  private TokenService() throws Exception {
    this.privateKey = readPrivateKey("/private_key.pem");
  }

  /**
   * Returns a valid JWT with hardcoded claims.
   *
   * @return a valid JWT
   * @throws Exception when parsing of private-key fails
   */
  public String generateTokenString() throws Exception {
    // Use the test private key associated with the test public key for a valid signature
    PrivateKey pk = readPrivateKey("/private_key.pem");
    return generateTokenString(pk, "/private_key.pem");
  }

  private String generateTokenString(PrivateKey privateKey, String kid) throws Exception {
    JwtClaimsBuilder claims = Jwt.claims();
    claims.issuer("https://quarkus.io/using-jwt-rbac");
    claims.subject("jdoe-using-jwt-rbac");
    claims.upn("jdoe@quarkus.io");
    claims.preferredUserName("jdoe");
    claims.audience("using-jwt-rbac");
    claims.groups(Set.of("Echoer", "Tester", "Subscriber", "group2"));
    claims.claim("birthday", "2001-07-13");
    long currentTimeInSecs = currentTimeInSecs();
    HashMap<String, Long> timeClaims = new HashMap<>();
    long exp = currentTimeInSecs + 24 * 3600; // one day valid token
    claims.issuedAt(currentTimeInSecs);
    claims.claim(Claims.auth_time.name(), currentTimeInSecs);
    claims.expiresAt(exp);
    return claims.jws().signatureKeyId(kid).sign(privateKey);
  }

  private PrivateKey readPrivateKey(final String pemResName) throws Exception {
    try (InputStream contentIS = TokenService.class.getResourceAsStream(pemResName)) {
      byte[] tmp = new byte[4096];
      int length = contentIS.read(tmp);
      return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }
  }

  private PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(keySpec);
  }

  private PublicKey decodePublicKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(keySpec);
  }

  private byte[] toEncodedBytes(final String pemEncoded) {
    final String normalizedPem = removeBeginEnd(pemEncoded);
    return Base64.getDecoder().decode(normalizedPem);
  }

  private String removeBeginEnd(String pem) {
    pem = pem.replaceAll("-----BEGIN (.*)-----", "");
    pem = pem.replaceAll("-----END (.*)----", "");
    pem = pem.replaceAll("\r\n", "");
    pem = pem.replaceAll("\n", "");
    return pem.trim();
  }

  private int currentTimeInSecs() {
    long currentTimeMS = System.currentTimeMillis();
    return (int) (currentTimeMS / 1000);
  }
}
