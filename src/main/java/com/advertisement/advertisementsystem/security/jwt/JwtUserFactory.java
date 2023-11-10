package com.advertisement.advertisementsystem.security.jwt;

import com.advertisement.advertisementsystem.model.entity.Role;
import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.model.enums.Status;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(final User user) {
        return JwtUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.getStatus().equals(Status.ACTIVE))
                .lastPasswordResetDate(user.getLastUpdateDate())
                .authorities(mapToGrantedAuthorities(user.getRoles()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(final List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
