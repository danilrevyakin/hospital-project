package com.example.hospitalproject.security.repository;

import com.example.hospitalproject.security.node.Representative;
import com.example.hospitalproject.security.node.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepository extends Neo4jRepository<Representative, Long> {
    Representative getByRole(Role role);
}
