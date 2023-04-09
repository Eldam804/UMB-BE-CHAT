package umb.chatApp.authentication.persistence.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

public class TokenEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String token;

}
