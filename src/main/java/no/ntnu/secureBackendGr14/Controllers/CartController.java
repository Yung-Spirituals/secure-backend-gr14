package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.ShoppingCartService;
import no.ntnu.secureBackendGr14.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class CartController {
  @Autowired
  private ShoppingCartService shoppingCartService;

  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Removes the first 8 chars of the string.
   * @param authHeader entire string.
   * @return string without first 8 chars.
   */
  private String getJwtFromHeader(String authHeader){
    return authHeader.substring(8);
  }

  /**
   * Reads the username from the authentication token.
   * @param authorization the token.
   * @return username.
   */
  private String getUsername(String authorization){
    return jwtUtil.extractUsername(getJwtFromHeader(authorization));
  }

  /**
   * Returns every cart in the users shopping cart.
   * @param authorization the token that will be turned into the username.
   * @return every cart in the users shopping cart.
   */
  @GetMapping("/cart")
  public ResponseEntity<?> getCart(@RequestHeader("authorization") String authorization){
    return new ResponseEntity<>(shoppingCartService.getCarts(getUsername(authorization)), HttpStatus.OK);
  }

  /**
   * Add products to the shopping cart.
   * @param productId
   * @param quantity
   * @param authorization
   * @return
   */
  @PutMapping("/cart/{productId}/{quantity}")
  public ResponseEntity<?> updateCart(@PathVariable Long productId,
                                      @PathVariable Integer quantity,
                                      @RequestHeader("authorization") String authorization){
    String status = shoppingCartService.addToCarts(getUsername(authorization), productId, quantity);
    if (status == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/cart/{productId}")
  public ResponseEntity<?> removeCart(@RequestHeader("authorization") String authorization,
                                      @PathVariable Long productId){
    String status = shoppingCartService.removeFromCarts(getUsername(authorization), productId);
    if (status == null){
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/cart")
  public ResponseEntity<?> emptyCart(@RequestHeader("authorization") String authorization){
    if (shoppingCartService.deleteCarts(authorization)){
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
