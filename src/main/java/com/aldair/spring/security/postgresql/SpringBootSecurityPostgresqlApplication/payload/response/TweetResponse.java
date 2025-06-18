package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class TweetResponse {
    private Long id;
    private String tweet;
    //private String imageUrl;
    private UserMinResponse postedBy;
    private List<CommentResponse> comments;
    private Map<String, Integer> reactions;
    private Timestamp createdAt;  // Nueva propiedad para la fecha de creación

    public TweetResponse(Long id, String tweet, /*String imageUrl,*/ UserMinResponse postedBy,
                         List<CommentResponse> comments,
                         Map<String, Integer> reactions, Timestamp createdAt) {
        this.id = id;
        this.tweet = tweet;
        //this.imageUrl = imageUrl;
        this.postedBy = postedBy;
        this.comments = comments != null ? comments : List.of();
        this.reactions = reactions != null ? reactions : Map.of();
        this.createdAt = createdAt;  // Inicialización de createdAt
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    /*
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    */

    public UserMinResponse getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(UserMinResponse postedBy) {
        this.postedBy = postedBy;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public Map<String, Integer> getReactions() {
        return reactions;
    }

    public void setReactions(Map<String, Integer> reactions) {
        this.reactions = reactions;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
