package com.ucsal.clinic.atendimento.dto;

import com.ucsal.clinic.atendimento.model.TipoAtendimento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AtendimentoRequest(
        @NotNull Long prontuarioId,
        @NotNull Long profissionalId,
        Long medicacaoId,
        @Positive Integer quantidadeMedicacaoUtilizada,
        @NotNull TipoAtendimento tipoAtendimento,
        LocalDateTime dataHoraInicio,
        String sintomas,
        String diagnostico,
        String medicaoDosagem,
        String tratamentoIndicado
) {
}
