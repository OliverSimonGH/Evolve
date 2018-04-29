package com.nsa.evolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EvolveApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvolveApplication.class, args);
    }
}
