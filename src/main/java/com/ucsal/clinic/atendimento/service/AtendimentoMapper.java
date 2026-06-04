package com.ucsal.clinic.atendimento.service;

import com.ucsal.clinic.atendimento.dto.AtendimentoRequest;
import com.ucsal.clinic.atendimento.dto.AtendimentoResponse;
import com.ucsal.clinic.atendimento.model.Atendimento;
import com.ucsal.clinic.atendimento.model.AtendimentoStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AtendimentoMapper {

    public Atendimento toModel(AtendimentoRequest request) {
        Atendimento atendimento = new Atendimento();
        atendimento.setProntuarioId(request.prontuarioId());
        atendimento.setProfissionalId(request.profissionalId());
        atendimento.setMedicacaoId(request.medicacaoId());
        atendimento.setQuantidadeMedicacaoUtilizada(request.quantidadeMedicacaoUtilizada());
        atendimento.setTipoAtendimento(request.tipoAtendimento());
        atendimento.setStatus(AtendimentoStatus.EM_ANDAMENTO);
        atendimento.setDataHoraInicio(request.dataHoraInicio() == null ? LocalDateTime.now() : request.dataHoraInicio());
        atendimento.setSintomas(request.sintomas());
        atendimento.setDiagnostico(request.diagnostico());
        atendimento.setMedicaoDosagem(request.medicaoDosagem());
        atendimento.setTratamentoIndicado(request.tratamentoIndicado());
        return atendimento;
    }

    public AtendimentoResponse toResponse(Atendimento atendimento) {
        return new AtendimentoResponse(
                atendimento.getId(),
                atendimento.getProntuarioId(),
                atendimento.getProfissionalId(),
                atendimento.getMedicacaoId(),
                atendimento.getQuantidadeMedicacaoUtilizada(),
                atendimento.getTipoAtendimento(),
                atendimento.getStatus(),
                atendimento.getDataHoraInicio(),
                atendimento.getDataHoraEncerramento(),
                atendimento.getSintomas(),
                atendimento.getDiagnostico(),
                atendimento.getMedicaoDosagem(),
                atendimento.getTratamentoIndicado()
        );
    }
}
