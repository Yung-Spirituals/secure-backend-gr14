package no.ntnu.secureBackendGr14.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private String description;

    private String image_path;

    public Product() {
    }

    /**
     * Creates an instance of a product.
     *
     * @param name        name of the product.
     * @param price       price of the product.
     * @param description description of the product.
     * @param image_path  image path of the product.
     */
    public Product(String name, int price, String description, String image_path) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image_path = image_path;
    }

    /**
     * Returns the id of the product.
     *
     * @return the id of the product.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set the id of the product.
     *
     * @param id to be set for the product.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the product.
     *
     * @param name to be set for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the product.
     *
     * @return the description of the product.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description of the product.
     *
     * @param description to be set for the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the price of the product.
     *
     * @return the price of the product.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Set the price of the product.
     *
     * @param price to be set for the product.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Returns the image path for the product.
     *
     * @return the image path for the product.
     */
    public String getImage_path() {
        return this.image_path;
    }

    /**
     * Set the image path for the product.
     *
     * @param image_path to be set for the product.
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}