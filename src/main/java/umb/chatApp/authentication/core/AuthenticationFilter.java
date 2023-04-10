package umb.chatApp.authentication.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import umb.chatApp.authentication.UserRole;
import umb.chatApp.authentication.service.AuthenticationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        //UserRole userRole = authenticationService.authenticate(token);
        UserRole userRole = new UserRole();
        userRole.setName("User");
        userRole.setId(1L);
        //System.out.println("AUTHENTICATION SERVICE" + userRole.getName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        //TODO FIX
        authorities.add(new SimpleGrantedAuthority("User"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userRole, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
