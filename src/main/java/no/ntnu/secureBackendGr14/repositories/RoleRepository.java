package no.ntnu.secureBackendGr14.repositories;

import no.ntnu.secureBackendGr14.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //TODO: add some custom find methods
}