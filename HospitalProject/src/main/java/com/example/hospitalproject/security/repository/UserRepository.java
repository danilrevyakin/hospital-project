package com.example.hospitalproject.security.repository;


import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByPhoneNumber(String phoneNumber);

    @Query("MATCH (u:`User`)-[:represents]-(r:`Representative`) WHERE r.role = $role RETURN u")
    Optional<List<User>> getUsersByRole(Role role);

    Optional<User> getUserById(Long id);

    void deleteUserById(Long id);

    void deleteUserByEmail(String email);

    @Query("match(u:User{email: :#{#user.email}}),(r:Representative{role: :#{#user.role}}) create(u)-[l:represents]->(r)")
    void connect(@Param("user") User user);
}
