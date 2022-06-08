package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Returns every cart in the users shopping cart.
     *
     * @param authorization the token that will be turned into the username.
     * @return every cart in the users shopping cart.
     */
    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(@RequestHeader("authorization") String authorization) {
        return new ResponseEntity<>(shoppingCartService.getCarts(jwtUtil.getUsername(authorization)), HttpStatus.OK);
    }

    /**
     * Add products to the shopping cart.
     *
     * @param productId
     * @param quantity
     * @param authorization
     * @return
     */
    @PostMapping("/update-cart/{productId}/{quantity}")
    public ResponseEntity<?> updateCart(@PathVariable Long productId,
                                        @PathVariable Integer quantity,
                                        @RequestHeader("authorization") String authorization) {
        String status = shoppingCartService.updateCart(jwtUtil.getUsername(authorization), productId, quantity);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-cart/{productId}")
    public ResponseEntity<?> removeCart(@RequestHeader("authorization") String authorization,
                                        @PathVariable Long productId) {
        String status = shoppingCartService.removeFromCart(jwtUtil.getUsername(authorization), productId);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/empty-cart")
    public ResponseEntity<?> emptyCart(@RequestHeader("authorization") String authorization) {
        if (shoppingCartService.deleteCarts(jwtUtil.getUsername(authorization))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
