package no.ntnu.secureBackendGr14.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private boolean processed;

    private Timestamp dateProcessed;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long idOfUser;

    private String username;

    private Integer quantity;

    private Timestamp dateCreated;

    public Order() {
    }

    public Order(Product product, User user, Integer quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.processed = false;
        this.idOfUser = user.getId();
        this.username = user.getUsername();
        this.dateProcessed = null;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
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
        this.idOfUser = user.getId();
        this.username = user.getUsername();
    }

    public Long getIdOfUser() {
        return this.idOfUser;
    }

    public void setIdOfUser(Long userId) {
        this.idOfUser = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (processed){
            setDateProcessed(new Timestamp(System.currentTimeMillis()));
        } else {
            setDateProcessed(null);
        }
    }

    public Timestamp getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateProcessed() {
        return this.dateProcessed;
    }

    public void setDateProcessed(Timestamp dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
}