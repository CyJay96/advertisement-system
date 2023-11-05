package com.advertisement.advertisementsystem.security;

import com.advertisement.advertisementsystem.model.entity.User;
import com.advertisement.advertisementsystem.security.jwt.JwtUserFactory;
import com.advertisement.advertisementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.findEntityByUsername(username);
        return JwtUserFactory.create(user);
    }
}
