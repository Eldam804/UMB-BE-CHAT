package umb.chatApp.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umb.chatApp.register.UnregUserDto;
import umb.chatApp.register.UnregUserResponse;
import umb.chatApp.register.service.RegisterService;

@RestController
public class RegisterController {
    //FOR TESTING PURPOSES
    //@Autowired
    //private RegisterService registerService;
    @Autowired
    private RegisterService registerService;
    @PostMapping("/api/register")
    UnregUserResponse registerUser(@RequestBody UnregUserDto unregUserDto){
        return this.registerService.createAccount(unregUserDto);
        //this.emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
    }

    @PostMapping("/api/validate/{userId}/{code}")
    void createUser(@PathVariable Long userId, @PathVariable Long code){
        this.registerService.confirmAccount(userId, code);
    }
}
