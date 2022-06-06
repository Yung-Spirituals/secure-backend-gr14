package no.ntnu.secureBackendGr14.repositories;

import no.ntnu.secureBackendGr14.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Save stuff from database, get stuff from database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}