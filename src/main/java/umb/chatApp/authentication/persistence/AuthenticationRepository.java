package umb.chatApp.authentication.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import umb.chatApp.authentication.UserRole;
import umb.chatApp.authentication.persistence.entity.TokenEntity;

public interface AuthenticationRepository extends Neo4jRepository<TokenEntity, Long> {

    @Query("MATCH (u:User) WHERE id(u) = $id " +
            "CREATE (t:Token {token: $token}) " +
            "CREATE (u)-[:HAS]->(t)")
    void createToken(Long id, String token);

    @Query("MATCH (u:User)-[:HAS]-(t:Token {token: $token}) DELETE DETACH t")
    void destroyToken(String token);

    @Query("MATCH (u:User)-[:HAS]-(t:Token {token: $token}) MATCH (u)-[:HASROLE]-(r) return r as UserRole")
    UserRole getUserRoleByToken(String token);

    @Query("MATCH(t:Token) WHERE t.token = $token return t as Token")
    TokenEntity getByToken(String token);
}
