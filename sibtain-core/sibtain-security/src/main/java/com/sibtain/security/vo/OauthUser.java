package com.sibtain.security.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class OauthUser implements UserDetails {
    private List<GrantedAuthority> authorities;
    private String password;
    private String userName;
    private boolean enabled;

    public OauthUser() {
    }

    public OauthUser(List<GrantedAuthority> authorities, String password, String userName, boolean enabled) {
        this.authorities = authorities;
        this.password = password;
        this.userName = userName;
        this.enabled = enabled;
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
        return userName;
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
        return enabled;
    }
}
