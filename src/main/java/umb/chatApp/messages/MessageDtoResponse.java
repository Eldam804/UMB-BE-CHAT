package umb.chatApp.messages;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Date;

public class MessageDtoResponse {
    @Id
    @GeneratedValue
    private Long id;
    private String timestamp;
    private String content;
    private String sentBy;

    public MessageDtoResponse(Long id, String timestamp, String content, String sentBy) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
        this.sentBy = sentBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
