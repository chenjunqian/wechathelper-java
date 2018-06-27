package com.wechathelper.service;

import com.wechathelper.model.JwtUser;
import com.wechathelper.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(Collections.singleton(user.getRoles().toString()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
