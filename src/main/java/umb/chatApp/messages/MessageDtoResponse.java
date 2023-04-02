package umb.chatApp.messages;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Date;

public class MessageDtoResponse {
    @Id
    @GeneratedValue
    private Long messageId;
    private String timestamp;
    private String content;
    private String sentBy;

    public MessageDtoResponse(Long messageId, String timestamp, String content, String sentBy) {
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.content = content;
        this.sentBy = sentBy;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
