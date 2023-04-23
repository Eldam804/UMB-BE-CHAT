package umb.chatApp.register;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String usernameAlreadyExists) {
        super(usernameAlreadyExists);
    }
}
