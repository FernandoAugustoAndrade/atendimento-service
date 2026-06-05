package com.ucsal.clinic.atendimento.client;

import com.ucsal.clinic.atendimento.dto.MedicacaoEstoqueRequest;
import com.ucsal.clinic.atendimento.exception.IntegracaoException;
import com.ucsal.clinic.atendimento.exception.ItemNaoEncontradoException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class MedicacaoClient {

    private final RestClient restClient;
    private final String baseUrl;

    public MedicacaoClient(
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder,
            @Value("${services.medicacao.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.baseUrl = baseUrl;
    }

    public void consumirEstoque(Long medicacaoId, Integer quantidade) {
        try {
            restClient.patch()
                    .uri(baseUrl + "/medicacoes/{id}/consumir-estoque", medicacaoId)
                    .body(new MedicacaoEstoqueRequest(quantidade))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new ItemNaoEncontradoException("Medicação não encontrada ou estoque insuficiente");
                    })
                    .toBodilessEntity();

        } catch (ItemNaoEncontradoException exception) {
            throw exception;
        } catch (RestClientException exception) {
            throw new IntegracaoException("Não foi possível atualizar o estoque no medicacao-service");
        }
    }
}