package no.ntnu.secureBackendGr14.models;

import javax.persistence.*;

@Entity(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    private Product product;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    private User user;

    private int quantity;

    public User getUser() {
        return user;
    }

    public ShoppingCart() {}

    public ShoppingCart(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}