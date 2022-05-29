package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.models.AddProductRequest;
import no.ntnu.secureBackendGr14.models.Product;
import no.ntnu.secureBackendGr14.services.ProductService;
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
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest newProduct) {
        String status = productService.add(newProduct);
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

    @PatchMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody AddProductRequest updatedProduct) {
        String status = productService.update(productId, updatedProduct);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }
}