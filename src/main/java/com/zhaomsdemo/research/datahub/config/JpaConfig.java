package com.zhaomsdemo.research.datahub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "")
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware () {
        return new SecurityAuditorAware();
    }
}