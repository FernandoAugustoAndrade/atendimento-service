# atendimento-service

Microservico responsavel pelo inicio, encerramento e consulta de atendimentos da clinica.

## Papel na arquitetura

- Registra-se no Service Discovery como `atendimento-service`.
- E chamado pela API Gateway/orquestrador.
- Consulta outros microservicos por service discovery:
  - `prontuario-service`
  - `profissional-service`
  - `medicacao-service`
- Mantem banco proprio e armazena apenas os ids externos, evitando acoplamento com tabelas de outros dominios.

## Endpoints

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/atendimentos` | Inicia um atendimento |
| `PATCH` | `/atendimentos/{id}/encerrar` | Encerra um atendimento em andamento |
| `GET` | `/atendimentos/{id}` | Consulta um atendimento pelo id |
| `GET` | `/atendimentos` | Lista todos os atendimentos |
| `GET` | `/atendimentos?status=EM_ANDAMENTO` | Lista atendimentos por status |
| `GET` | `/atendimentos/prontuario/{prontuarioId}` | Lista atendimentos de um prontuario |

## Exemplo de inicio

```json
{
  "prontuarioId": 1,
  "profissionalId": 2,
  "medicacaoId": 3,
  "quantidadeMedicacaoUtilizada": 1,
  "tipoAtendimento": "CONSULTA",
  "sintomas": "Dor de cabeca",
  "diagnostico": "Enxaqueca",
  "medicaoDosagem": "Dipirona 500mg",
  "tratamentoIndicado": "Repouso e hidratacao"
}
```

## Configuracao esperada

```properties
spring.application.name=atendimento-service
server.port=8084
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

services.prontuario.base-url=http://prontuario-service
services.profissional.base-url=http://profissional-service
services.medicacao.base-url=http://medicacao-service
```

## Observacao para integracao

O `medicacao-service` deve expor o endpoint `PATCH /medicacoes/{id}/consumir-estoque`, recebendo:

```json
{
  "quantidade": 1
}
```

Esse contrato evita que o `atendimento-service` altere diretamente o banco ou a regra de estoque de outro dominio.
