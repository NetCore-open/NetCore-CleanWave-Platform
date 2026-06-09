package com.netcore.cleanwave.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CleanWavePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanWavePlatformApplication.class, args);
    }

}

