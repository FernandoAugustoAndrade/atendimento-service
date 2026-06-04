package com.ucsal.clinic.atendimento.client;

import com.ucsal.clinic.atendimento.exception.IntegracaoException;
import com.ucsal.clinic.atendimento.exception.ItemNaoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ProfissionalClient {

    private final RestClient restClient;
    private final String baseUrl;

    public ProfissionalClient(RestClient.Builder restClientBuilder,
                              @Value("${services.profissional.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.build();
        this.baseUrl = baseUrl;
    }

    public void validarExistencia(Long profissionalId) {
        try {
            restClient.get()
                    .uri(baseUrl + "/profissionais-saude/{id}", profissionalId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new ItemNaoEncontradoException("Profissional de saude nao encontrado");
                    })
                    .toBodilessEntity();
        } catch (ItemNaoEncontradoException exception) {
            throw exception;
        } catch (RestClientException exception) {
            throw new IntegracaoException("Nao foi possivel consultar o profissional-service");
        }
    }
}
