package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.models.Product;
import no.ntnu.secureBackendGr14.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;


  /**
   * Request to return all the products from the database.
   * @return
   */
  @GetMapping("/get-products")
  public ResponseEntity<?> getProducts() {
    return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
  }


  /**
   * Request to add a new product to the database. This is only doable through an admin user.
   * @param product to be added.
   * @return HttpStatus Ok or HttpStatus Bad_request.
   */
  @PostMapping("/add-product")
  public ResponseEntity<?> addProduct(@RequestBody Product product) {
    String status = productService.add(product);
    if (status == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
  }


  /**
   * Delete a product by id from the database. This is only doable through an admin user.
   * @param productId to be deleted
   * @return HttpStatus Ok or HttpStatus Bad_request.
   */
  @DeleteMapping("/delete-product/{productId}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
    String status = productService.delete(productId);
    if (status == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Update existing product in the database. Sends an instance of a product with the desired
   * attribute values. This is only doable through an admin user.
   * @param productId which item to be updated.
   * @param updatedProduct product to be updated.
   * @return HttpStatus Ok or HttpStatus Bad_request.
   */
  @PutMapping("/update-product/{productId}")
  public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                         @RequestBody Product updatedProduct) {
    String status = productService.update(productId, updatedProduct);
    if (status == null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
  }
}
