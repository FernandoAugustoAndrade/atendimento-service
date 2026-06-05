package com.ucsal.clinic.atendimento.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProfissionalClient {

    private final RestClient restClient;
    private final String baseUrl;

    public ProfissionalClient(
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder,
            @Value("${services.profissional.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.baseUrl = baseUrl;
    }

    public void validarExistencia(Long profissionalId) {
        restClient.get()
                .uri(baseUrl + "/profissionais-saude/{id}", profissionalId)
                .retrieve()
                .toBodilessEntity();
    }
}