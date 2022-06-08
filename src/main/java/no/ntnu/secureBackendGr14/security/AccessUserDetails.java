package no.ntnu.secureBackendGr14.security;

import no.ntnu.secureBackendGr14.models.Role;
import no.ntnu.secureBackendGr14.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AccessUserDetails implements UserDetails {

    private final String username;

    private final String password;

    private final boolean active;

    private final List<GrantedAuthority> authorities = new LinkedList<>();

    public AccessUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        getRoles(user.getRoles());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private void getRoles(Set<Role> roles) {
        this.authorities.clear();
        for (Role role : roles) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }
}