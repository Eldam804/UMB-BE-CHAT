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

import java.lang.reflect.Array;
import java.util.*;

@Service
public class MessageService {

    private final List<String> BAD_WORDS = Arrays.asList("fuck", "shit", "ffs");
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
        List<UserMessageDto> userMessageDtos = messageRepository.getPrivateMessage(messageRequestDto.getUserId(), messageRequestDto.getOtherUserId());
        List<MessageDtoResponse> messageDtoResponses = new ArrayList<MessageDtoResponse>();
        for (UserMessageDto userMessageDto : userMessageDtos) {
            messageDtoResponses.add(entityToDto(userMessageDto));
        }
        messageDtoResponses.sort(Comparator.comparing(MessageDtoResponse::getTimestamp));
        return messageDtoResponses;
    }

    public void sendMessageToGlobalChat(MessageDto messageDto) {
        messageRepository.sentGlobalMessage(filter(messageDto.getMessageContent()), messageDto.getSentById());
    }

    public void sendMessageToPrivateChat(Long sentTo, MessageDto messageDto) {
        messageRepository.sentPrivateMessage(sentTo, messageDto.getMessageContent(), messageDto.getSentById());
    }

    private String filter(String message){
        String Replacement = "*";
        String[] words = message.split(" ");
        for(int i = 0; i < words.length; i++){
            if(BAD_WORDS.contains(words[i])){
                words[i] = Replacement.repeat(words[i].length());
            }
        }
        return String.join(" ",words);
    }

}
