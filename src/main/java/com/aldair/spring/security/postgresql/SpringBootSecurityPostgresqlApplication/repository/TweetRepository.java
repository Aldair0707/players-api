package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT DISTINCT t FROM Tweet t " +
       "LEFT JOIN FETCH t.comments c " +
       "LEFT JOIN FETCH c.user " +
       "LEFT JOIN FETCH t.postedBy")
       List<Tweet> findAllWithComments(); 
}
