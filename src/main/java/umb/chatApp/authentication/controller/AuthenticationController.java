package umb.chatApp.authentication.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umb.chatApp.authentication.service.AuthenticationService;

import java.util.Base64;
import java.util.Optional;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/api/login")
    public void loginUser(@RequestHeader(value = "Authorization", required = false)Optional<String> authentication, HttpServletResponse response){
        System.out.println(authentication.get());
        if(authentication.isEmpty()){
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        System.out.println();
        String[] credentials = decodeBasicAuthHeader(authentication.get());
        String token = authenticationService.authenticate(credentials[0], credentials[1]);
        System.out.println(credentials[0] + credentials[1]);
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("Authorization", "Bearer " + token);
    }

    @DeleteMapping("/api/login")
    public void logoutUser(@RequestHeader(value = "Authorization", required = true) Optional<String> authentication){
        String token = authentication.get().substring("Bearer".length()).trim();
        authenticationService.logout(token);
    }
    private String[] decodeBasicAuthHeader(String authHeader) {
        byte[] decodedBytes = Base64.getDecoder().decode(authHeader.substring(6));
        String decodedString = new String(decodedBytes);
        return decodedString.split(":", 2);
    }
}
