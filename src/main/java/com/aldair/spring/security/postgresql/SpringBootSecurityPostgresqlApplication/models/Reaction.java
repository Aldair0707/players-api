package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private EReaction name;

    public Reaction() {}

    public Reaction(EReaction name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public EReaction getName() {
        return name;
    }

    public void setName(EReaction name) {
        this.name = name;
    }
}

