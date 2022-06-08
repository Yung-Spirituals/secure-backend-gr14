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

  /**
   * If the user and products both exist, then it will first check if the user already has a cart
   * with that product, if the product is present then the new specified quantity for that product.
   * If the product is not there, then a new cart will be created.
   *
   * @param username  users shopping cart.
   * @param productId id of the product.
   * @param quantity  of the product.
   * @return null. Suppose to return error message, but not implemented yet.
   */
  public String updateCart(String username, Long productId, Integer quantity) {
    boolean alreadyInCart = false;
    if (userRepository.findByUsername(username).isPresent() &&
        productRepository.existsById(productId)) {
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

  /**
   * Checks if the user exist, if the user exist then it will check of the user has a shopping cart.
   * Finds the shopping cart for the product id, then deletes it.
   *
   * @param username  of user.
   * @param productId of product.
   * @return null. Suppose to return error message, but not implemented yet.
   */
  public String removeFromCart(String username, Long productId) {
    String errorMessage = null;
    if (userRepository.findByUsername(username).isPresent()) {
      User user = userRepository.findByUsername(username).get();
      if (!user.getShoppingCarts().isEmpty()) {
        for (ShoppingCart shoppingCart : user.getShoppingCarts()) {
          if (shoppingCart.getProduct().getId().equals(productId)) {
            shoppingCartRepository.delete(shoppingCart);
            shoppingCartRepository.flush();
          }
        }
      }
    }
    return errorMessage;
  }

  /**
   * If the user exist, then we use the user id to find all the shopping carts belonging to the user.
   * If the user does not exist, returns null.
   *
   * @param username of user.
   * @return list of shopping carts or null.
   */
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

  /**
   * Deletes all the carts that a user has.
   *
   * @param username of user.
   * @return true of deleted, false if user is not found.
   */
  public boolean deleteCarts(String username) {
    if (userRepository.findByUsername(username).isPresent()) {
      shoppingCartRepository.deleteAll(
          userRepository.findByUsername(username).get().getShoppingCarts());
      shoppingCartRepository.flush();
      return true;
    } else {
      return false;
    }
  }
}