package no.ntnu.secureBackendGr14.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for handling JWT tokens
 * Code from https://youtu.be/X80nJ5T7YpE
 */
@Component
public class JwtUtil {

  @Value("${jwt_secret_key}")
  private String SECRET_KEY;

  /**
   * Key inside JWT token where roles are stored
   */
  private static final String JWT_AUTH_KEY = "roles";

  /**
   * Generate a JWT token for an authenticated user
   *
   * @param userDetails Object containing user details
   * @return JWT token string
   */
  public String generateToken(UserDetails userDetails) {
    final long TIME_NOW = System.currentTimeMillis();
    final long MILLISECONDS_IN_HOUR = 60 * 60 * 1000;
    final long TIME_AFTER_ONE_HOUR = TIME_NOW + MILLISECONDS_IN_HOUR;

    //Creates the jwt token
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim(JWT_AUTH_KEY, userDetails.getAuthorities())
        .setIssuedAt(new Date(TIME_NOW))
        .setExpiration(new Date(TIME_AFTER_ONE_HOUR))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  /**
   * Find username from a JWT token
   *
   * @param token JWT token
   * @return Username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Check if a token is valid for a given user
   *
   * @param token       Token to validate
   * @param userDetails Object containing user details
   * @return True if the token matches the current user and is still valid
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * Extract when the token expires.
   *
   * @param token to be checked.
   * @return expiration date.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts attribute from the given jwt token.
   *
   * @param token          to be extracted.
   * @param claimsResolver what to retrieve.
   * @param <T>
   * @return attribute.
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all attributes from the given jwt token.
   *
   * @param token to be extracted.
   * @return attributes.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  /**
   * Checks of the jwt token is expired.
   *
   * @param token to be checked.
   * @return true if expired, false if not.
   */
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Removes the first 7 chars of the string.
   *
   * @param authHeader entire string.
   * @return string without first 7 chars.
   */
  private String getJwtFromHeader(String authHeader) {
    return authHeader.substring(7);
  }

  /**
   * Reads the username from the authentication token.
   *
   * @param authorization the token.
   * @return username.
   */
  public String getUsername(String authorization) {
    return extractUsername(getJwtFromHeader(authorization));
  }
}