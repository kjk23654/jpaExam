package com.example.configs;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private String userId;

    public AuditorAwareImpl(String userId) {

        if(userId == null) {
            userId = "user1";
        }
        this.userId = userId;
    }

    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of(userId);
    }
}
