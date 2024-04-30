package com.java.authentication.repository;

import com.java.authentication.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT ur.role.name FROM UserRole ur WHERE ur.user.username = :username")
    List<String> findRoleNamesByUsername(@Param("username") String username);
}