package umb.chatApp.messages;

public class MessageDto {
    private String messageContent;
    private String sentBy;
    private Long sentById;
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSentBy() {
        return sentBy;
    }

    public Long getSentById() {
        return sentById;
    }

    public void setSentById(Long sentById) {
        this.sentById = sentById;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
