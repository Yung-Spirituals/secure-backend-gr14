package no.ntnu.secureBackendGr14.security;


/**
 * The data we will send as a response to the user when the authentication is successful
 */
public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}