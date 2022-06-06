package no.ntnu.secureBackendGr14.repositories;

import no.ntnu.secureBackendGr14.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Save stuff from database, get stuff from database
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
