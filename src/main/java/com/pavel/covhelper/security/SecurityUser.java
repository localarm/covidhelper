package com.pavel.covhelper.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private final String username;
    private final String password;
    private final long departmentId;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String username, String password, long departmentId, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.departmentId = departmentId;
        this.authorities = authorities;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
