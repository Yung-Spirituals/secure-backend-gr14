package no.ntnu.secureBackendGr14.repositories;

import no.ntnu.secureBackendGr14.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
}