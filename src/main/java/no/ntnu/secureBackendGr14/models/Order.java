package no.ntnu.secureBackendGr14.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @JsonIgnore
    @ManyToOne
    private User user;

    private Long userId;

    private String username;

    private Integer quantity;

    private boolean processed;

    public Order() {
    }

    public Order(Product product, User user, Integer quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.processed = false;
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return this.id;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
        this.username = user.getUsername();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isProcessed() {
        return this.processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}