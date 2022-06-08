package no.ntnu.secureBackendGr14.security;

public class AuthenticationRequest {

    private String username;

    private String password;

    public AuthenticationRequest() {
    }

    /**
     * Constructor for authentication request.
     * @param username of user.
     * @param password of user.
     */
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get users username.
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username of user.
     * @param username of user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password of user.
     * @return password of user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password of the user.
     * @param password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}