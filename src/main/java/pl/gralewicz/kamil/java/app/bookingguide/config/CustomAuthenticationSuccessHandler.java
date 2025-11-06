package pl.gralewicz.kamil.java.app.bookingguide.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        String redirectUrl = null;

        for (GrantedAuthority authority : grantedAuthorities) {
            String role = authority.getAuthority();

            if ("ROLE_ADMIN".equals(role)) {
                redirectUrl = "/users/dashboard";
                break;
            } else  if ("ROLE_USER".equals(role)){
                redirectUrl = "/clients/dashboard";
                break;
            }
        }

        if (redirectUrl == null) {
            redirectUrl = "/services";
        }

        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }
}
