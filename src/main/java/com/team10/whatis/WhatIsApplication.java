package com.team10.whatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WhatIsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatIsApplication.class, args);
    }

}
