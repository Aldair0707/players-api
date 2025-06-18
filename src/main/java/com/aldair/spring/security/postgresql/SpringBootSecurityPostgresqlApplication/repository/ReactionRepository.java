package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByName(EReaction name);
    boolean existsByName(EReaction name);
}
