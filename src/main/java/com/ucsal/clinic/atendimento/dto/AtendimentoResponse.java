package com.ucsal.clinic.atendimento.dto;

import com.ucsal.clinic.atendimento.model.AtendimentoStatus;
import com.ucsal.clinic.atendimento.model.TipoAtendimento;

import java.time.LocalDateTime;

public record AtendimentoResponse(
        Long id,
        Long prontuarioId,
        Long profissionalId,
        Long medicacaoId,
        Integer quantidadeMedicacaoUtilizada,
        TipoAtendimento tipoAtendimento,
        AtendimentoStatus status,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraEncerramento,
        String sintomas,
        String diagnostico,
        String medicaoDosagem,
        String tratamentoIndicado
) {
}
