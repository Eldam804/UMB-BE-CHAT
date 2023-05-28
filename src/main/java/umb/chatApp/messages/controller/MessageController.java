package umb.chatApp.messages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umb.chatApp.messages.*;
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

    @PostMapping("/api/group-message/")
    public void createGroupChat(@RequestBody GroupChatDto groupChatDto){
        messageService.createGroupChat(groupChatDto);
    }

    @GetMapping("/api/group-message/{groupId}")
    public List<MessageDtoResponse> getGroupChatMessages(@PathVariable Long groupId){
        return messageService.getGroupChatMessages(groupId);
    }
    @PostMapping("/api/group-message/{groupId}")
    public void postGroupChatMessage(@PathVariable Long groupId, @RequestBody MessageDto messageDto){
        messageService.sendMessageToGroupChat(messageDto, groupId);
    }





    @GetMapping("/api/user/invites/{userId}")
    public List<GroupChatResponse> getUsersInvites(@PathVariable Long userId){
        return this.messageService.getUsersByInvites(userId);
    }

    @GetMapping("/api/user/groups/{userId}")
    public List<GroupChatResponse> getGroupsOfUser(@PathVariable Long userId){
        return this.messageService.getGroupsOfUser(userId);
    }

    @PostMapping("/api/user/invites/{groupId}/{userId}")
    public void acceptUserInvite(@PathVariable Long userId, @PathVariable Long groupId){
        this.messageService.acceptUserInvite(userId, groupId);
    }
    @DeleteMapping("/api/user/invites/{groupId}/{userId}")
    public void declineUserInvite(@PathVariable Long userId, @PathVariable Long groupId){
        this.messageService.declinetUserInvite(userId, groupId);
    }
}
