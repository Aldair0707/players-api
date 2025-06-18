package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Tweet;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.TweetReaction;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface TweetReactionRepository extends JpaRepository<TweetReaction, Long> {
    Optional<TweetReaction> findByTweetAndUser(Tweet tweet, User user);
    List<TweetReaction> findByTweetId(Long tweetId);
}
