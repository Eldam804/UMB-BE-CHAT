package umb.chatApp.messages.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import umb.chatApp.messages.GroupChatResponse;
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
            " RETURN {id: id(u1), username: u1.username, password: u1.password, joinDate: toString(u1.joinDate), description: u1.description} as user,"+
            " {id: id(m), timestamp: toString(datetime({epochMillis: m.timestamp})), content: m.content} as message"+
            " UNION"+
            " MATCH (u2:User)-[:SENT]->(m:PrivateMessage)-[:TO]->(u1:User)"+
            " WHERE id(u1) = $userId AND id(u2) = $otherUserId"+
            " RETURN {id: id(u2), username: u2.username, password: u2.password, joinDate: toString(u2.joinDate), description: u2.description} as user,"+
            " {id: id(m), timestamp: toString(datetime({epochMillis: m.timestamp})), content: m.content} as message" +
            " ORDER BY m.timestamp"
    )
    List<UserMessageDto> getPrivateMessage(Long userId, Long otherUserId);

    @Query("MATCH (u:User) WHERE id(u) = $sentBy " +
            " CREATE (g:GlobalMessage {content: $messageContent, timestamp: timestamp()})" +
            " CREATE (u)-[:SENT]->(g)")
    void sentGlobalMessage(String messageContent, Long sentBy);

    @Query("MATCH (u1:User), (u2:User) WHERE id(u1) = $sentBy AND id(u2) = $sentTo" +
            " CREATE (p:PrivateMessage {content: $messageContent, timestamp: timestamp()})" +
            " CREATE (u1)-[:SENT]->(p)-[:TO]->(u2)")
    void sentPrivateMessage(Long sentTo, String messageContent, Long sentBy);

    @Query("CREATE (g:GroupChat {groupName: $groupName}) return id(g) AS id, g.groupName AS groupName")
    GroupChatResponse createGroupChat(String groupName);

    @Query("MATCH(g:GroupChat) WHERE g.groupName = $groupName return id(g) AS id, g.groupName AS groupName")
    GroupChatResponse returnGroupChat(String groupName);

    @Query("MATCH (gc:GroupChat) WHERE id(gc) = $groupId" +
            " MATCH (u:User) WHERE id(u) = $sentById" +
            " CREATE(g:GroupChatMessage {content: $messageContent, timestamp: timestamp()})" +
            " CREATE (u)-[:SENT]->(g)-[:TO]->(gc)")
    void sentGroupChat(Long groupId, String messageContent, Long sentById);

    @Query("MATCH(u:User)-[:SENT]->(gm:GroupChatMessage)-[:TO]->(g:GroupChat) WHERE id(g) = $groupId" +
            " return {id: id(u), username: u.username, password: u.password, joinDate: toString(u.joinDate), description: u.description} as user," +
            " {id: id(gm), timestamp: toString(datetime({epochMillis: gm.timestamp})), content: gm.content} as message ORDER BY gm.timestamp")
    List<UserMessageDto> getGroupChatMessages(Long groupId);

    @Query("MATCH(u:User) WHERE id(u) = $userId" +
            " MATCH(g:GroupChat) WHERE id(g) = $id" +
            " CREATE (g)-[:INVITED]->(u)")
    void inviteUserById(Long userId, Long id);



///FIX

    @Query("MATCH(g:GroupChat)-[:INVITED]->(u:User) WHERE id(u) = $id return id(g) AS id, g.groupName as groupName")
    List<GroupChatResponse> getInvitesById(Long id);

    @Query("MATCH(u:User), (g:GroupChat) WHERE id(u) = $userId AND id(g) = $groupId" +
            " MATCH(g)-[i:INVITED]->(u) DELETE i" +
            " CREATE (g)-[:ACCEPTED]->(u)")
    void acceptInvite(Long userId, Long groupId);


    @Query("MATCH(g:GroupChat)-[:ACCEPTED]->(u:User) WHERE id(u) = $userId RETURN id(g) AS id, g.groupName as groupName")
    List<GroupChatResponse> getAllGroupsOfUser(Long userId);

    @Query("MATCH(u:User), (g:GroupChat) WHERE id(u) = $userId AND id(g) = $groupId" +
            " MATCH(g)-[i:INVITED]->(u) DELETE i")
    void declineInvite(Long userId, Long groupId);


    @Query("MATCH(u:User), (g:GroupChat) WHERE id(u) = $userId AND id(g) = $groupId" +
            " CREATE(g)-[:INVITED]->(u)")
    void inviteNewUser(Long userId, Long groupId);
}
