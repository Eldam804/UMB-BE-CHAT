package umb.chatApp.messages;


public class MessageRequestDto {
    private Long userId;
    private Long otherUserId;

    public MessageRequestDto(Long userId, Long otherUserId) {
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
    }
}
