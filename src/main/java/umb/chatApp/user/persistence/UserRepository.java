package umb.chatApp.user.persistence;

import org.apache.catalina.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import umb.chatApp.messages.GroupChatResponse;
import umb.chatApp.user.UserDtoResponse;
import umb.chatApp.user.persistence.entity.UserEntity;

import java.util.List;


@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {


    @Query("MATCH(u:User) RETURN u")
    List<UserEntity> getAllUsers();
    @Query("MATCH(u:User) WHERE id(u) = $id")
    UserEntity getUserById(Long id);

    @Query("MATCH (u:User) WHERE u.username = $username RETURN u")
    UserEntity getUserByUsername(String username);

    @Query("MATCH (u:User)-[:HAS]->(t:Token {token: $token}) return u")
    UserEntity getUserByToken(String token);

    @Query("CREATE(u:User {username: $username, password: $password, email: $email, description: $description, joinDate: $joinDate})")
    void createUser(String username, String password, String email, String description, String joinDate);

    @Query("MATCH(u:User) where id(u)=$id set u.username=$username, u.description=$description")
    void editUser(Long id, String username, String description);
}
