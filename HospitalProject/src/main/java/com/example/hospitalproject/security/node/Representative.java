package com.example.hospitalproject.security.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;


@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Representative {
    @Id @GeneratedValue
    private Long id;

    @Relationship(type = "represents", direction = Relationship.Direction.INCOMING)
    private List<User> users;

    private Role role;
}
