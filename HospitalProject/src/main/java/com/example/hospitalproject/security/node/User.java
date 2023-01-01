package com.example.hospitalproject.security.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NodeEntity("User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String phoneNumber;
    private String password;

    @Relationship(type = "represents")
    private Representative representative;

    @Enumerated(EnumType.STRING)
    private Role role;
}
