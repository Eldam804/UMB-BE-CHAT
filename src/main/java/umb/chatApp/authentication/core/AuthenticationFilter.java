package umb.chatApp.authentication.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import umb.chatApp.authentication.UserRole;
import umb.chatApp.authentication.service.AuthenticationService;

import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
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
        System.out.println(token.length());
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        //TokenDto tokenEntity =

        Boolean tokenExists = this.authenticationService.findToken(token);
        if(!tokenExists){
            throw new AuthenticationCredentialsNotFoundException("Incorrect token");
        }
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
