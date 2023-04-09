package umb.chatApp.authentication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umb.chatApp.authentication.persistence.AuthenticationRepository;
import umb.chatApp.authentication.persistence.entity.TokenEntity;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.service.UserService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserService userService;

    public String login(String username, String password) {
        Optional<UserDtoResponse> userDtoResponse = Optional.ofNullable(userService.getUserByUsername(username));
        if(userDtoResponse.isEmpty()){
            //throw new AuthenticationCredentialsNotFoundException("USERNAME PASSWORD do not match!");
        }
        if(Objects.equals(userDtoResponse.get().getPassword(), password)){
            //throw new AuthenticationCredentialsNotFoundException("USERNAME PASSWORD do not match!");
        }
        String randomString = UUID.randomUUID().toString();
        TokenEntity token = new TokenEntity();
        token.setToken(randomString);

        authenticationRepository.createToken(userDtoResponse.get().getId(), token.getToken());
        return token.getToken();
    }

    public void logout(String token) {

    }
}
