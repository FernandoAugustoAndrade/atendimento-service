package com.ucsal.clinic.atendimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AtendimentoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtendimentoServiceApplication.class, args);
    }
}
