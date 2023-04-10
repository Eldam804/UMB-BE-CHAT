package umb.chatApp.authentication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import umb.chatApp.authentication.UserRole;
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

    public String authenticate(String username, String password) {
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

    public UserRole authenticate(String token){
        String token1 = token.substring("Bearer".length()).trim();
        TokenEntity optionalTokenEntity = authenticationRepository.getByToken(token1);
        if(optionalTokenEntity.getToken().isEmpty()){
            throw new AuthenticationCredentialsNotFoundException("Authentication failed");
        }
        //System.out.println(authenticationRepository.getUserRoleByToken(token).getName());
        return authenticationRepository.getUserRoleByToken(token);
    }

    public void logout(String token) {
        authenticationRepository.destroyToken(token);
    }
}
