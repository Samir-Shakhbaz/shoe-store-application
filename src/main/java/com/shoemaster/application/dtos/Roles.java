package com.shoemaster.application.dtos;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

    ROLE_ADMIN, ROLE_USER, ROLE_SUPERUSER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
