package umb.chatApp.user.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import umb.chatApp.user.UserDtoResponse;

import java.util.List;


@Repository
public interface UserRepository extends Neo4jRepository<UserDtoResponse, Long> {


    @Query("MATCH(u:User) RETURN u")
    List<UserDtoResponse> getAllUsers();
    @Query("MATCH(u:User) WHERE id(u) = $id")
    UserDtoResponse getUserById(Long id);

}
