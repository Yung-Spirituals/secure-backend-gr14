package no.ntnu.secureBackendGr14.security;

import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessUserService implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent())
        {
            return new AccessUserDetails((user.get()));
        }
        else
        {
            throw new UsernameNotFoundException(username + " not found!");
        }
    }
}
