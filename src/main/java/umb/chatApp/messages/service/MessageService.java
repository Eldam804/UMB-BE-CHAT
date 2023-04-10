package umb.chatApp.messages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import umb.chatApp.messages.MessageDto;
import umb.chatApp.messages.MessageDtoResponse;
import umb.chatApp.messages.MessageRequestDto;
import umb.chatApp.messages.UserMessageDto;
import umb.chatApp.messages.persistence.MessageRepository;
import umb.chatApp.messages.persistence.entity.MessageEntity;
import umb.chatApp.user.UserDtoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@PreAuthorize("hasROLE('User')")
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    public List<MessageDtoResponse> getAllGlobalMessages() {
        List<UserMessageDto> userMessageDtos = messageRepository.getAllMessages();
        System.out.println(userMessageDtos.get(0).getMessage().getContent());
        List<MessageDtoResponse> messageDtoResponses = new ArrayList<MessageDtoResponse>();

        for (UserMessageDto userMessageDto : userMessageDtos) {
            messageDtoResponses.add(entityToDto(userMessageDto));
        }
        return messageDtoResponses;
    }

    private MessageDtoResponse entityToDto(UserMessageDto userMessageDto) {
        MessageDtoResponse messageDtoResponse = new MessageDtoResponse(userMessageDto.getMessage().getId(), userMessageDto.getMessage().getTimestamp(),
                    userMessageDto.getMessage().getContent(), userMessageDto.getUser().getUsername()
                );
        return messageDtoResponse;

    }


    public List<MessageDtoResponse> getPrivateMessagesById(MessageRequestDto messageRequestDto) {
        return messageRepository.getPrivateMessage(messageRequestDto.getUserId(), messageRequestDto.getOtherUserId());
    }

    public void sendMessageToGlobalChat(MessageDto messageDto) {
        messageRepository.sentGlobalMessage(messageDto.getMessageContent(), messageDto.getSentById());
    }

    public void sendMessageToPrivateChat(Long sentTo, MessageDto messageDto) {
        messageRepository.sentPrivateMessage(sentTo, messageDto.getMessageContent(), messageDto.getSentById());
    }
}
