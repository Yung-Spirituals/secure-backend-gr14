package no.ntnu.secureBackendGr14.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    public User getUser() {
        return user;
    }

    public ShoppingCart() {
    }

    /**
     * Constructor for shopping cart.
     * @param user of shopping cart.
     * @param product in shopping cart.
     * @param quantity of product.
     */
    public ShoppingCart(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Get id of shopping cart.
     * @return id of shopping cart.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id of shopping cart.
     * @param id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get products in shopping cart.
     * @return products in shopping cart.
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Get quantity of that one product.
     * @return quantity of products.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Set quantity of that one product.
     * @param quantity of product.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}