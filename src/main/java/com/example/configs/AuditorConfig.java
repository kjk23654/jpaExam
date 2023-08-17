package com.example.configs;

import com.querydsl.core.annotations.Config;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    public AuditorAware<String> auditorProvider() {

        String userId = null;
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attr != null) {
            HttpSession session = attr.getRequest().getSession();
            userId = (String)session.getAttribute("userId");
        }

        return new AuditorAwareImpl(userId);

    }
}
