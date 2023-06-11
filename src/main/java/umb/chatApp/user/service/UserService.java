package umb.chatApp.user.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umb.chatApp.messages.GroupChatResponse;
import umb.chatApp.user.UserDtoRequest;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.UserEditDto;
import umb.chatApp.user.persistence.UserRepository;
import umb.chatApp.user.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public UserDtoResponse getUserByUsername(String username) {
        Optional<UserEntity> userEntity = Optional.ofNullable(this.userRepository.getUserByUsername(username));
        if(userEntity.isEmpty()){
            return null;
        }
        return mapToDto(userEntity.get());
    }

    public UserDtoResponse getUserByToken(String token){
        token = token.substring(7);
        Optional<UserEntity> userEntity = Optional.ofNullable(this.userRepository.getUserByToken(token));
        if(userEntity.isEmpty()){
            return null;
        }
        return mapToDto(userEntity.get());
    }

    public void createUser(UserDtoRequest userDtoRequest) {
        this.userRepository.createUser(userDtoRequest.getUsername(), userDtoRequest.getPassword(), userDtoRequest.getEmail(), userDtoRequest.getDescription(), userDtoRequest.getJoinDate());
    }

    public void editUserById(Long id, UserEditDto user) {
        this.userRepository.editUser(id, user.getUsername(), user.getDescription());
    }
}
