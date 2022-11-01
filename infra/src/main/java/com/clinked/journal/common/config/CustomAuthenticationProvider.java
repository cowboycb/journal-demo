package com.clinked.journal.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

        final String username = (authentication.getPrincipal() == null) ? "" : authentication.getName();
        final String password = authentication.getCredentials().toString();
        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException("invalid login credentials");
        }
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new BadCredentialsException("invalid login credentials");
            }
        } catch (UsernameNotFoundException exception) {
            throw new BadCredentialsException("invalid login credentials");
        }
        return createSuccessfulAuthentication(authentication, user);
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(user.getUsername(), authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }




}