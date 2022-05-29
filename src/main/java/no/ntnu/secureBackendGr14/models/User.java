package no.ntnu.secureBackendGr14.models;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    private String username;
    private String password;
    private boolean active = true;

    public User(){}


    /**
     * Creates an instance of a user.
     * @param username username of the user.
     * @param password password of the user.
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the id of the user.
     * @return the id of the user.
     */
    public Long getId()
    {
        return this.id;
    }

    /**
     * Set the id of the user.
     * @param id to be set for the user.
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Returns the role of the user.
     * @return the role of the user.
     */
    public Set<Role> getRoles()
    {
        return this.roles;
    }

    /**
     * Set the role of the user
     * @param roles to be set for the user.
     */
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    /**
     * Adds a role to the user.
     * @param role to be added to the user.
     */
    public void addRole(Role role)
    {
        this.roles.add(role);
    }

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Set the username for the user.
     * @param username to be set for the user.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Set password for the user.
     * @param password to be set for the user.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns if the user is active.
     * @return user activity.
     */
    public boolean getActive()
    {
        return this.active;
    }

    /**
     * Set the activity of the user.
     * @param active to be set for the user.
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }
}
