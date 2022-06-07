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
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  public String registerUser(String username, String password){
    String errorMessage = null;
    if(username.isBlank() || password.isBlank()){
      errorMessage = "Username or password field are blank";
    }
    if(userRepository.findByUsername(username).isPresent()){
      errorMessage = "Username is already taken";
    }
    if(errorMessage == null) {
      User user = new User(username, password);
      if (roleRepository.findByName("USER").isPresent()){
        user.addRole(roleRepository.findByName("USER").get());
      }
      userRepository.save(user);
    }
    return errorMessage;
  }
}
