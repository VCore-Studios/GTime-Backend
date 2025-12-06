package com.vblocks.gtime;

import com.vblocks.gtime.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.vblocks")
public class GtimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GtimeApplication.class, args);
    }

}
