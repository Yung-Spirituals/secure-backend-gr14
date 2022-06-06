package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.models.Product;
import no.ntnu.secureBackendGr14.models.ShoppingCart;
import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.ProductService;
import no.ntnu.secureBackendGr14.services.ShoppingCartService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String testPublic() {
        return "This is public :)";
    }

    @GetMapping("/user")
    public String testUser() {
        return "This is user level :D";
    }

    @GetMapping("/admin")
    public String testAdmin() {
        return "This is admin level!!! ;D";
    }

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        String status = productService.add(product);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        String status = productService.delete(productId);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody Product updatedProduct) {
        String status = productService.update(productId, updatedProduct);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCart(@RequestHeader("authorization") String authorization){
        return new ResponseEntity<>(shoppingCartService.getCarts(getUsername(authorization)), HttpStatus.OK);
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

    //TODO: Implement this
    /*@PostMapping("/purchase")
    public String makeOrder(@RequestHeader Authorization authorization){
        //logic
    }*/

    private String getJwtFromHeader(String authHeader){
        return authHeader.substring(8);
    }

    private String getUsername(String authorization){
        return jwtUtil.extractUsername(getJwtFromHeader(authorization));
    }
}