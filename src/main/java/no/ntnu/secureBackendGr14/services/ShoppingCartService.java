package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.ShoppingCart;
import no.ntnu.secureBackendGr14.models.User;
import no.ntnu.secureBackendGr14.repositories.ProductRepository;
import no.ntnu.secureBackendGr14.repositories.ShoppingCartRepository;
import no.ntnu.secureBackendGr14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try {
            Long id = null;
            User user = null;
            if (userRepository.findByUsername(username).isPresent()) {
                user = userRepository.findByUsername(username).get();
            }
            if (user != null) {
                for (ShoppingCart shoppingCart : user.getShoppingCarts()) {
                    if (shoppingCart.getProduct().getId().equals(productId)) {
                        shoppingCart.setQuantity(quantity);
                        shoppingCartRepository.save(shoppingCart);
                        alreadyInCart = true;
                    }
                }
                if (!alreadyInCart && quantity > 0) {
                    shoppingCartRepository.save(
                            new ShoppingCart(user, productRepository.getById(productId), quantity));
                }
            }
        } catch (Exception e){
            return "Error: " + e.getMessage();
        }
        return null;
    }


    //TODO: make error message more specific
    public String removeFromCarts(String username, Long productId){
        String errorMessage = null;
        if (userRepository.findByUsername(username).isPresent()) {
            Long id = userRepository.findByUsername(username).get().getId();
            User user = userRepository.getById(id);
            for (ShoppingCart shoppingCart : user.getShoppingCarts()) {
                if (shoppingCart.getProduct().getId().equals(productId)) {
                    shoppingCartRepository.delete(shoppingCart);
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
        return shoppingCartRepository.findAllByUserId(id);
    }

    public boolean deleteCarts(String username){
        if (userRepository.findByUsername(username).isPresent()) {
            shoppingCartRepository.deleteAll(userRepository.findByUsername(username).get().getShoppingCarts());
            return true;
        }
        return false;
    }

    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }
}
