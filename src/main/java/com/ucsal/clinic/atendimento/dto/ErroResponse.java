package com.ucsal.clinic.atendimento.dto;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String erro,
        String mensagem,
        LocalDateTime dataHora
) {
}
