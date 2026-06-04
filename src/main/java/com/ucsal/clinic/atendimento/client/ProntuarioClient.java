package com.ucsal.clinic.atendimento.client;

import com.ucsal.clinic.atendimento.exception.IntegracaoException;
import com.ucsal.clinic.atendimento.exception.ItemNaoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ProntuarioClient {

    private final RestClient restClient;
    private final String baseUrl;

    public ProntuarioClient(RestClient.Builder restClientBuilder,
                            @Value("${services.prontuario.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.build();
        this.baseUrl = baseUrl;
    }

    public void validarExistencia(Long prontuarioId) {
        try {
            restClient.get()
                    .uri(baseUrl + "/prontuarios/{id}", prontuarioId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new ItemNaoEncontradoException("Prontuario nao encontrado");
                    })
                    .toBodilessEntity();
        } catch (ItemNaoEncontradoException exception) {
            throw exception;
        } catch (RestClientException exception) {
            throw new IntegracaoException("Nao foi possivel consultar o prontuario-service");
        }
    }
}
