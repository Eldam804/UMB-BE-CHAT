package umb.chatApp.messages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umb.chatApp.messages.MessageDto;
import umb.chatApp.messages.MessageDtoResponse;
import umb.chatApp.messages.MessageRequestDto;
import umb.chatApp.messages.service.MessageService;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/api/global-messages")
    public List<MessageDtoResponse> getAllGlobalMessages(){
        return messageService.getAllGlobalMessages();
    }
    @GetMapping("/api/private-messages/{userId}/{foreignId}")
    public List<MessageDtoResponse> getPrivateMessagesById(@PathVariable Long userId, @PathVariable Long foreignId){
        MessageRequestDto messageRequestDto = new MessageRequestDto(userId, foreignId);
        return messageService.getPrivateMessagesById(messageRequestDto);
    }
    @PostMapping("/api/global-messages")
    public void sendMessageToGlobalChat(@RequestBody MessageDto messageDto){
        messageService.sendMessageToGlobalChat(messageDto);
    }

    @PostMapping("/api/private-messages/{sentTo}")
    public void sendMessageToPrivateChat(@PathVariable Long sentTo, @RequestBody MessageDto messageDto){
        messageService.sendMessageToPrivateChat(sentTo, messageDto);
    }

}
