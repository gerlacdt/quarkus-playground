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
import org.eclipse.microprofile.jwt.Claims;

public class TokenUtils {
  private TokenUtils() {
    // no-op: utility class
  }

  /**
   * Utility method to generate a JWT string from a JSON resource file that is signed by the
   * privateKey.pem test resource key, possibly with invalid fields.
   *
   * @return the JWT string
   * @throws Exception on parse failure
   */
  public static String generateTokenString() throws Exception {
    // Use the test private key associated with the test public key for a valid signature
    PrivateKey pk = readPrivateKey("/private_key.pem");
    return generateTokenString(pk, "/private_key.pem");
  }

  public static String generateTokenString(PrivateKey privateKey, String kid) throws Exception {
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

  /**
   * Read a PEM encoded private key from the classpath
   *
   * @param pemResName - key file resource name
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
    try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
      byte[] tmp = new byte[4096];
      int length = contentIS.read(tmp);
      return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
    }
  }

  /**
   * Decode a PEM encoded private key string to an RSA PrivateKey
   *
   * @param pemEncoded - PEM string for private key
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(keySpec);
  }

  public static PublicKey decodePublicKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(keySpec);
  }

  private static byte[] toEncodedBytes(final String pemEncoded) {
    final String normalizedPem = removeBeginEnd(pemEncoded);
    return Base64.getDecoder().decode(normalizedPem);
  }

  private static String removeBeginEnd(String pem) {
    pem = pem.replaceAll("-----BEGIN (.*)-----", "");
    pem = pem.replaceAll("-----END (.*)----", "");
    pem = pem.replaceAll("\r\n", "");
    pem = pem.replaceAll("\n", "");
    return pem.trim();
  }

  /** @return the current time in seconds since epoch */
  public static int currentTimeInSecs() {
    long currentTimeMS = System.currentTimeMillis();
    return (int) (currentTimeMS / 1000);
  }
}
