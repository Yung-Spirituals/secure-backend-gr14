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
      //password is 1234
      User potato = new User("potato", "$2a$12$l0UlrXdiiYc8oQTeaP/7cuT0480jGj9x12.NPRwu4ThwHiYLtQ6Me");
      //password is onion
      User onion = new User("onion","$2a$12$ACv9t2jxzVIS8XfqS45XGejGRr4m3caVlJU.9ntxeP/rt.ixo.gAy");
      //password is 5555
      User tomato = new User("tomato", "$2a$12$10xmupguHxpRMGYOaclPEOlHN8PuHTdfg.B/Dj4PM7LIe0sjPrDrm");

      Role user = new Role("ROLE_USER");
      Role admin = new Role("ROLE_ADMIN");

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
