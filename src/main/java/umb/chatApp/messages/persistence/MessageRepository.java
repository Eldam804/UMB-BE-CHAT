package umb.chatApp.messages.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import umb.chatApp.messages.MessageDtoResponse;
import umb.chatApp.messages.UserMessageDto;
import umb.chatApp.messages.persistence.entity.MessageEntity;

import java.util.List;


@Repository
public interface MessageRepository extends Neo4jRepository<MessageEntity, Long> {
    /*@Query("MATCH (u:User), (g:GlobalMessage)" +
            " MATCH (u)-[:SENT]->(g)" +
            " RETURN u as user, g as message")
    */
    @Query("MATCH (u:User)-[:SENT]->(g:GlobalMessage) return {id: id(u), username: u.username, password: u.password, joinDate: toString(u.joinDate), description: u.description} as user, {id: id(g), timestamp: toString(datetime({epochMillis: g.timestamp})), content: g.content} as message ORDER BY g.timestamp")
    List<UserMessageDto> getAllMessages();

    @Query("MATCH (u1:User)-[:SENT]->(m:PrivateMessage)-[:TO]->(u2:User)" +
            " WHERE id(u1) = $userId AND id(u2) = $otherUserId"+
            " RETURN m"+
            " UNION"+
            " MATCH (u1:User)-[:SENT]->(m:PrivateMessage)-[:TO]->(u2:User)"+
            " WHERE id(u1) = $userId AND id(u2) = $otherUserId"+
            " RETURN m;"
    )
    List<MessageDtoResponse> getPrivateMessage(Long userId, Long otherUserId);

    @Query("MATCH (u:User) WHERE id(u) = $sentBy " +
            " CREATE (g:GlobalMessage {content: $messageContent, timestamp: timestamp()})" +
            " CREATE (u)-[:SENT]->(g)")
    void sentGlobalMessage(String messageContent, Long sentBy);

    @Query("MATCH (u1:User), (u2:User) WHERE id(u1) = $sentBy AND id(u2) = $sentTo" +
            " CREATE (p:PrivateMessage {content: $messageContent, timestamp: timestamp()})" +
            " CREATE (u1)-[:SENT]->(p)-[:TO]->(u2)")
    void sentPrivateMessage(Long sentTo, String messageContent, Long sentBy);
}
