package umb.chatApp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umb.chatApp.messages.GroupChatResponse;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.UserEditDto;
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

    @GetMapping("/api/user/details/{token}")
    public UserDtoResponse getUserByToken(@PathVariable String token){
        return this.userService.getUserByToken(token);
    }

    @PutMapping("/api/user/{id}")
    public void editUserById(@PathVariable Long id, @RequestBody UserEditDto user){
        this.userService.editUserById(id, user);
    }
}
