package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.ReactionRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ReactionRepository reactionRepository;

    public DataInitializer(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public void run(String... args) {
        for (EReaction r : EReaction.values()) {
            boolean exists = reactionRepository.existsByName(r);
            if (!exists) {
                Reaction newReaction = new Reaction();
                newReaction.setName(r);
                reactionRepository.save(newReaction);
                System.out.println("Inserted reaction: " + r.name());
            }
        }
    }
}
