package no.ntnu.secureBackendGr14.models;

public class AddProductRequest {
  private String name;
  private int price;
  private String description;
  private String image_path;

  /**
   *
   * @param name
   * @param price
   * @param description
   * @param image_path
   */
  public AddProductRequest(String name, int price, String description, String image_path) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.image_path = image_path;
  }

  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public int getPrice() {
    return price;
  }

  /**
   *
   * @param price
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   *
   * @return
   */
  public String getDescription() {
    return description;
  }

  /**
   *
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   *
   * @return
   */
  public String getImage_path() {
    return image_path;
  }

  /**
   *
   * @param image_path
   */
  public void setImage_path(String image_path) {
    this.image_path = image_path;
  }
}