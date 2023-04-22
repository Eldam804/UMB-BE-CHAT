package umb.chatApp.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import umb.chatApp.register.EmailDto;
import umb.chatApp.register.UnregUser;
import umb.chatApp.register.UnregUserDto;
import umb.chatApp.register.UnregUserResponse;
import umb.chatApp.register.persistence.RegisterRepository;
import umb.chatApp.user.UserDtoRequest;
import umb.chatApp.user.persistence.entity.UserEntity;
import umb.chatApp.user.service.UserService;

import java.util.Random;

@Service
public class RegisterService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private UserService userService;

    public UnregUserResponse createAccount(UnregUserDto unregUserDto){
        int leftLimit = 1000;
        int rightLimit = 5000;
        int rand = (int) (Math.random() * (rightLimit - leftLimit) + leftLimit);
        Long generatedValue = (long) rand;
        EmailDto emailDto = new EmailDto();
        emailDto.setBody("Authorization code: " + generatedValue);
        emailDto.setTo(unregUserDto.getEmail());
        emailDto.setSubject("Register to chat application");
        sendEmail(emailDto);
        UnregUser unregUser = registerRepository.registerUser(unregUserDto.getUsername(), unregUserDto.getPassword(), unregUserDto.getEmail(), unregUserDto.getDescription(), generatedValue);
        UnregUserResponse unregUserResponse = new UnregUserResponse();
        unregUserResponse.setId(unregUser.getId());
        unregUserResponse.setDescription(unregUser.getDescription());
        unregUserResponse.setEmail(unregUser.getEmail());
        unregUserResponse.setUsername(unregUser.getUsername());
        unregUserResponse.setJoinDate(unregUser.getJoinDate());
        return unregUserResponse;
    }

    public void confirmAccount(Long userId, Long code){
        UnregUser unregUser = this.registerRepository.confirmAccount(userId, code);
        UserDtoRequest userDtoRequest = new UserDtoRequest();
        userDtoRequest.setUsername(unregUser.getUsername());
        userDtoRequest.setPassword(unregUser.getPassword());
        userDtoRequest.setDescription(unregUser.getDescription());
        userDtoRequest.setEmail(unregUser.getEmail());
        userDtoRequest.setJoinDate(unregUser.getJoinDate());
        this.userService.createUser(userDtoRequest);
        this.registerRepository.deleteTemporaryAcc(userId, code);
    }

    private void sendEmail(EmailDto emailDto) {
        this.emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
    }
}
