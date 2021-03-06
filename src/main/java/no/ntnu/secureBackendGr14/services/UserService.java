package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.RoleRepository;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Checks if the username and password attributes are blank or the username is already taken.
     * If it does not then it registers a new user to the database, and gives it the user role.
     *
     * @param username of user.
     * @param password of user.
     * @return either an error message or null.
     */
    public String registerUser(String username, String password) {
        String errorMessage = null;
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Username or password field are blank";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            errorMessage = "Username is already taken";
        }
        if (errorMessage == null) {
            User user = new User(username, passwordEncoder.encode(password));
            if (roleRepository.findByName("USER").isPresent()) {
                user.addRole(roleRepository.findByName("USER").get());
            }
            userRepository.save(user);
        }
        return errorMessage;
    }
}