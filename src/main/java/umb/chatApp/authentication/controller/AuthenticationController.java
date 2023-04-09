package umb.chatApp.authentication.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import umb.chatApp.authentication.service.AuthenticationService;

import java.util.Optional;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/api/login")
    public void loginUser(@RequestHeader(value = "Authorization", required = false)Optional<String> authentication, HttpServletResponse response){
        if(authentication.isEmpty()){
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        String[] credentials = new String[]{authentication.get()};
        String token = authenticationService.login(credentials[0], credentials[1]);

        response.setStatus(HttpStatus.OK.value());
        response.addHeader("Authorization", "Bearer " + token);
    }

    @DeleteMapping("/api/login")
    public void logoutUser(@RequestHeader(value = "Authorization", required = true) Optional<String> authentication){
        String token = authentication.get().substring("Bearer".length()).trim();
        authenticationService.logout(token);
    }
}
