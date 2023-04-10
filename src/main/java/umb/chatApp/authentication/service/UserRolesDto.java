package umb.chatApp.authentication.service;

import java.util.Set;

public class UserRolesDto {
    private final String username;
    private final Set<String> roles;

    public UserRolesDto(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
