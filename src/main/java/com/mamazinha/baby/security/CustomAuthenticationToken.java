package com.mamazinha.baby.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String userId;

    public CustomAuthenticationToken(Object principal, Object credentials, String userId) {
        super(principal, credentials);
        this.userId = userId;
    }

    public CustomAuthenticationToken(
        Object principal,
        Object credentials,
        Collection<? extends GrantedAuthority> authorities,
        String userId
    ) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CustomAuthenticationToken) {
            return this.userId.equals(((CustomAuthenticationToken) obj).userId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }
}
