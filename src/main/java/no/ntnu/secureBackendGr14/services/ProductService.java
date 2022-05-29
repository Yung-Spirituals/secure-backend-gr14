package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.AddProductRequest;
import no.ntnu.secureBackendGr14.models.Product;
import no.ntnu.secureBackendGr14.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public String update(Long productId, AddProductRequest updatedInfo) {
        String errorMessage = null;
        if (productExists(productId)) {
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


    public String delete(Long productId) {
        String errorMessage = null;
        if (productExists(productId)) {
            try {
                productRepository.delete(productRepository.getById(productId));
            } catch (Exception e) {
                errorMessage = "Error deleting product from database: " + e.getMessage();
            }
        } else {
            errorMessage = "Product does not exist!";
        }
        return errorMessage;
    }

    public String add(AddProductRequest product) {
        String errorMessage = null;
        if (verifyProduct(product)) {
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

    private boolean verifyProduct(AddProductRequest product) {
        return !product.getName().isBlank() && !product.getDescription().isBlank()
                && !product.getImage_path().isBlank() && productRepository.findByName(product.getName()).isEmpty();
    }

    private boolean productExists(Long productId) {
        if (productId != null) {
            return productRepository.findById(productId).isPresent();
        } else {
            return false;
        }
    }
}