package umb.chatApp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user/{id}")
    public UserDtoResponse getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }
    @GetMapping("/api/user")
    public List<UserDtoResponse> getAllUsers(){
        return this.userService.getAllUsers();
    }
}
