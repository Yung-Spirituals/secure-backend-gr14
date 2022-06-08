package no.ntnu.secureBackendGr14.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "roles")
public class Role {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new LinkedHashSet<>();

  private String name;

  public Role() {
  }

  /**
   * Constructor for role.
   *
   * @param name of role.
   */
  public Role(String name) {
    this.name = name;
  }

  /**
   * Get id of the role.
   *
   * @return id of role.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Set if for role.
   *
   * @param id to be set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the users that have that one role.
   *
   * @return users that have that one role.
   */
  public Set<User> getUsers() {
    return this.users;
  }

  /**
   * Sets a role to a group of users.
   *
   * @param users that get the role.
   */
  public void setUsers(Set<User> users) {
    this.users = users;
  }

  /**
   * Adds user to the role.
   *
   * @param user to get the role.
   */
  public void addUser(User user) {
    this.users.add(user);
  }

  /**
   * Returns name of the role.
   *
   * @return name of the role.
   */
  public String getName() {
    return this.name;
  }
}