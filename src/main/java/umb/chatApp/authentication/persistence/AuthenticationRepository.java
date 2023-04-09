package umb.chatApp.authentication.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import umb.chatApp.authentication.persistence.entity.TokenEntity;

public interface AuthenticationRepository extends Neo4jRepository<TokenEntity, Long> {

    @Query("MATCH (u:User) WHERE id(u) = $id " +
            "CREATE (t:Token {token: $token}) " +
            "CREATE (u)-[:HAS]->(t)")
    void createToken(Long id, String token);
}
