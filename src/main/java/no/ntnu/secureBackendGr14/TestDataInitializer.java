package no.ntnu.secureBackendGr14;

import java.util.Optional;
import no.ntnu.secureBackendGr14.models.Role;
import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.RoleRepository;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  private final Logger logger = LoggerFactory.getLogger("TestInitializer");

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event){
    Optional<User> existingUser = userRepository.findByUsername("Aron");
    if (existingUser.isEmpty()){
      logger.info("Importing test data...");
      User potato = new User("potato", "1234");
      User onion = new User("onion","onion");
      User tomato = new User("tomato", "5555");

      Role user = new Role("USER");
      Role admin = new Role("ADMIN");

      potato.addRole(user);
      potato.addRole(admin);
      onion.addRole(user);
      tomato.addRole(admin);

      roleRepository.save(user);
      roleRepository.save(admin);

      userRepository.save(potato);
      userRepository.save(onion);
      userRepository.save(tomato);

      logger.info("Finished importing test data");
    }
    else{
      logger.info("User already exists, importing stopped");
    }
  }
}
