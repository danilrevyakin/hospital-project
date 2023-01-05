package com.example.hospitalproject.security.repository;


import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByPhoneNumber(String phoneNumber);
    Optional<List<User>> getUsersByRole(Role role);

    Optional<User> getUserById(Long id);
}
