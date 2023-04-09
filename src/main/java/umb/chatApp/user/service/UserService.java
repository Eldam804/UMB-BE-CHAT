package umb.chatApp.user.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.persistence.UserRepository;
import umb.chatApp.user.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserDtoResponse getUserById(Long id) {
        return mapToDto(this.userRepository.getUserById(id));
    }



    public List<UserDtoResponse> getAllUsers() {
        return mapToListDto(this.userRepository.getAllUsers());
    }

    private List<UserDtoResponse> mapToListDto(List<UserEntity> allUsers) {
        List<UserDtoResponse> userDtoResponses = new ArrayList<>();
        for (UserEntity userEntity: allUsers) {
            userDtoResponses.add(mapToDto(userEntity));
        }
        return userDtoResponses;
    }

    private UserDtoResponse mapToDto(UserEntity user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setUsername(user.getUsername());
        userDtoResponse.setPassword(user.getPassword());
        userDtoResponse.setId(user.getId());
        userDtoResponse.setDescription(user.getDescription());
        userDtoResponse.setJoinDate(user.getJoinDate());
        return userDtoResponse;
    }

}
