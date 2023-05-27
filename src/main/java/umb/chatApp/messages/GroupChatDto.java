package umb.chatApp.messages;

import java.util.List;

public class GroupChatDto {
    private String groupName;
    private List<Long> usersInvited;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Long> getUsersInvited() {
        return usersInvited;
    }

    public void setUsersInvited(List<Long> usersInvited) {
        this.usersInvited = usersInvited;
    }
}
