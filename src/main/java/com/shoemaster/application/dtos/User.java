package com.shoemaster.application.dtos;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
public class User implements UserDetails {

    private Long userId;

    private String username;

    private String password;

    private String email;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    private List<Roles> roles;

    private String fullName;

    private String address;

    private Long card;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}

