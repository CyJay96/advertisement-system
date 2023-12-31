package com.advertisement.advertisementsystem.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;

@Builder
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private final Long id;

    private final String username;

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final String email;

    private final String password;

    private final boolean enabled;

    private final OffsetDateTime lastPasswordResetDate;

    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public OffsetDateTime getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
