package com.ucsal.clinic.atendimento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MedicacaoEstoqueRequest(
        @NotNull @Positive Integer quantidade
) {
}
