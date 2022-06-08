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

  /**
   * Returns id of order.
   *
   * @return id of order.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Set id of order.
   *
   * @param id to be set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the product that order is for.
   *
   * @return the product that order is for.
   */
  public Product getProduct() {
    return this.product;
  }

  /**
   * Change the product that order is for.
   *
   * @param product that order is for.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Returns the user ordering.
   *
   * @return the user ordering.
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Change which user put the order.
   *
   * @param user that will put the order.
   */
  public void setUser(User user) {
    this.user = user;
    this.idOfUser = user.getId();
    this.username = user.getUsername();
  }

  /**
   * Returns the id of the user ordering.
   *
   * @return id of the user.
   */
  public Long getIdOfUser() {
    return this.idOfUser;
  }

  /**
   * Set the id of the user.
   *
   * @param userId of user.
   */
  public void setIdOfUser(Long userId) {
    this.idOfUser = userId;
  }

  /**
   * Get username of user.
   *
   * @return username of user.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Set username of user.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns quantity of product ordered.
   *
   * @return quantity of product ordered.
   */
  public Integer getQuantity() {
    return this.quantity;
  }

  /**
   * Set quantity of product ordered.
   *
   * @param quantity of product ordered.
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Processed or not.
   *
   * @return true if processed, false if not.
   */
  public boolean isProcessed() {
    return this.processed;
  }

  /**
   * Sets true if the order is processed and sets when it was processed. If processed is false,
   * then clear date processed.
   *
   * @param processed
   */
  public void setProcessed(boolean processed) {
    this.processed = processed;
    if (processed) {
      setDateProcessed(new Timestamp(System.currentTimeMillis()));
    } else {
      setDateProcessed(null);
    }
  }

  /**
   * Returns date when order was created.
   *
   * @return date when order was created.
   */
  public Timestamp getDateCreated() {
    return this.dateCreated;
  }

  /**
   * Sets date when order was created.
   *
   * @param dateCreated when order was created.
   */
  public void setDateCreated(Timestamp dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Returns date and time of when order was processed.
   *
   * @return date and time of when order was processed.
   */
  public Timestamp getDateProcessed() {
    return this.dateProcessed;
  }

  /**
   * Set date and time of when order was processed.
   *
   * @param dateProcessed date and time of when order was processed.
   */
  public void setDateProcessed(Timestamp dateProcessed) {
    this.dateProcessed = dateProcessed;
  }
}