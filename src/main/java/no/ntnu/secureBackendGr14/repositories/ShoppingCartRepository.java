package no.ntnu.secureBackendGr14.repositories;

import no.ntnu.secureBackendGr14.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Save stuff from database, get stuff from database
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserId(Long id);
}