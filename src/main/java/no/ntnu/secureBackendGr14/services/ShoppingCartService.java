package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.ShoppingCart;
import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.ProductRepository;
import no.ntnu.secureBackendGr14.repositories.ShoppingCartRepository;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    //TODO: make error message more specific
    public String addToCarts(String username, Long productId, Integer quantity) {
        boolean alreadyInCart = false;
        if (userRepository.findByUsername(username).isPresent() && productRepository.existsById(productId)) {
            User user = userRepository.findByUsername(username).get();
            for (ShoppingCart shoppingCart : user.getShoppingCarts()) {
                if (shoppingCart.getProduct().getId().equals(productId)) {
                    ShoppingCart cart = shoppingCartRepository.getById(shoppingCart.getId());
                    cart.setQuantity(quantity);
                    shoppingCartRepository.save(cart);
                    alreadyInCart = true;
                }
            }
            if (!alreadyInCart || user.getShoppingCarts().isEmpty()) {
                shoppingCartRepository.save(
                    new ShoppingCart(user, productRepository.getById(productId), quantity));
            }
        }
        return null;
    }

    //TODO: make error message more specific
    public String removeFromCarts(String username, Long productId) {
        String errorMessage = null;
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            if (user.getShoppingCarts().isEmpty()) {
                for (ShoppingCart shoppingCart : user.getShoppingCarts()) {
                    if (shoppingCart.getProduct().getId().equals(productId)) {
                        shoppingCartRepository.delete(shoppingCart);
                    }
                }
            }
        }
        return errorMessage;
    }

    public List<ShoppingCart> getCarts(String username) {
        Long id = null;
        if (userRepository.findByUsername(username).isPresent()) {
            id = userRepository.findByUsername(username).get().getId();
        }
        if (id != null) {
            return shoppingCartRepository.findAllByUserId(id);
        } else {
            return null;
        }
    }

    public boolean deleteCarts(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            shoppingCartRepository.deleteAll(userRepository.findByUsername(username).get().getShoppingCarts());
            shoppingCartRepository.flush();
            return true;
        }else {
            return false;
        }
    }

    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }
}
