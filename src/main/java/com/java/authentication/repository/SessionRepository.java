package com.java.authentication.repository;

import com.java.authentication.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Session findByToken(String token);
}
