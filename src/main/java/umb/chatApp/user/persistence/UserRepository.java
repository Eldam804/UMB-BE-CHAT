package umb.chatApp.user.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
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
}
