package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.Product;
import no.ntnu.secureBackendGr14.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Returns a list of all products in the database.
     * @return list of all products in the database.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Finds the product in the database using the product id and set all the attribute from the
     * updateInfo parameter.
     * @param productId id of the product to be updated.
     * @param updatedInfo product instance containing updated information.
     * @return errormessage.
     */
    public String update(Long productId, Product updatedInfo) {
        String errorMessage = null;
        if (productExists(productId) && verifyProduct(updatedInfo)) {
            try {
                Product product = productRepository.getById(productId);
                product.setName(updatedInfo.getName());
                product.setPrice(updatedInfo.getPrice());
                product.setDescription(updatedInfo.getDescription());
                product.setImage_path(updatedInfo.getImage_path());
                productRepository.save(product);
            } catch (Exception e) {
                errorMessage = "Error saving product to database: " + e.getMessage();
            }
        } else {
            errorMessage = "Product with given ID not found";
        }
        return errorMessage;
    }

    /**
     * If the product is found with the product id, deletes it from database.
     * @param productId of the product.
     * @return errormessage.
     */
    public String delete(Long productId) {
        String errorMessage = null;
        if (productExists(productId)) {
            try {
                productRepository.delete(productRepository.getById(productId));
                productRepository.flush();
            } catch (Exception e) {
                errorMessage = "Error deleting product from database: " + e.getMessage();
            }
        } else {
            errorMessage = "Product does not exist!";
        }
        return errorMessage;
    }

    /**
     * Adds a new product to the database, takes the product we want to add and verifies that it is
     * valid, if everything is fine then it gets saved to the database.
     * @param product to be added.
     * @return errormessage or null.
     */
    public String add(Product product) {
        String errorMessage = null;
        if (!verifyProduct(product) && productRepository.findByName(product.getName()).isPresent()) {
            errorMessage = "Product data invalid!";
        }
        if (errorMessage == null) {
            try {
                productRepository.save(new Product(product.getName(), product.getPrice(),
                        product.getDescription(), product.getImage_path()));
            } catch (Exception e) {
                errorMessage = "Error adding product to database: " + e.getMessage();
            }
        }
        return errorMessage;
    }

    /**
     * Checks if any of the attributes are blank.
     * @param product to be checked.
     * @return true if product is valid, false if not.
     */
    private boolean verifyProduct(Product product) {
        return !product.getName().isBlank() && !product.getDescription().isBlank()
                && !product.getImage_path().isBlank();
    }

    /**
     * Checks if the product with that id already in the database.
     * @param productId to be checked.
     * @return true of product exists, false if not.
     */
    private boolean productExists(Long productId) {
        if (productId != null) {
            return productRepository.findById(productId).isPresent();
        } else {
            return false;
        }
    }
}