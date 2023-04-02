package umb.chatApp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.persistence.UserRepository;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserDtoResponse getUserById(Long id) {
        return this.userRepository.getUserById(id);
    }

    public List<UserDtoResponse> getAllUsers() {
        return this.userRepository.getAllUsers();
    }
}
