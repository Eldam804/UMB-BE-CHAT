package umb.chatApp.register.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import umb.chatApp.register.UnregUser;
import umb.chatApp.register.UnregUserResponse;

public interface RegisterRepository extends Neo4jRepository<UnregUser, Long> {

    @Query("CREATE (u:UnregUser {username: $username, password: $password, email: $email, description: $description, joinDate: toString(date())})-[:HAS]->(c:Code {code: $code}) RETURN u")
    UnregUser registerUser(String username, String password, String email, String description, Long code);

    @Query("MATCH (u:UnregUser), (c:Code) WHERE id(u) = $userId AND c.code = $code MATCH (u)-[:HAS]->(c) RETURN u")
    UnregUser confirmAccount(Long userId, Long code);

    @Query("MATCH (u:UnregUser), (c:Code) WHERE id(u) = $userId AND c.code = $code MATCH (u)-[:HAS]->(c) DELETE DETACH u, c")
    void deleteTemporaryAcc(Long userId, Long code);
}
