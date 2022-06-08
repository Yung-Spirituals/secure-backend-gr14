package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import no.ntnu.secureBackendGr14.security.AccessUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds the user by username, if the user is not found, it throws an exception.
     * @param username to be found.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new AccessUserDetails((user.get()));
        } else {
            throw new UsernameNotFoundException(username + " not found!");
        }
    }
}