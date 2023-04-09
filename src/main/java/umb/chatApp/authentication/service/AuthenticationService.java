package umb.chatApp.authentication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umb.chatApp.authentication.persistence.AuthenticationRepository;
import umb.chatApp.user.service.UserService;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserService userService;

    public String login(String username, String password) {

        if(username.isEmpty()){

        }
        return "";
    }

    public void logout(String token) {
    }
}
