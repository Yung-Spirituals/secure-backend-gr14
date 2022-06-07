package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.security.AccessUserDetails;
import no.ntnu.secureBackendGr14.security.AuthenticationResponse;
import no.ntnu.secureBackendGr14.security.AuthenticationRequest;
import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Checks if the user exist and that the credentials are correct. Tries to authenticate, if it
     * succeeds then a new jwt get created using the user details and then returns the token to the client.
     * If it fails then the server sends a HttpStatus unauthorized.
     * @param authenticationRequest username and password.
     * @return a jwt token to the client.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwtUtil = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtUtil));
    }

    /**
     * Calls for the register user method in the userService class, this method returns an error
     * message if something goes wrong.
     * @param user username and password.
     * @return HttpStatus Ok or HttpStatus bad_request.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest user) {
        String error = userService.registerUser(user.getUsername(), user.getPassword());
        if(error == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}