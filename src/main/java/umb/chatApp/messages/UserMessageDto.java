package umb.chatApp.messages;

import umb.chatApp.messages.persistence.entity.MessageEntity;
import umb.chatApp.user.UserDtoResponse;

public class UserMessageDto {
    private UserDtoResponse user;
    private MessageEntity message;

    public UserDtoResponse getUser() {
        return user;
    }

    public void setUser(UserDtoResponse user) {
        this.user = user;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }
}
