package com.ucsal.clinic.atendimento.service;

import com.ucsal.clinic.atendimento.client.MedicacaoClient;
import com.ucsal.clinic.atendimento.client.ProfissionalClient;
import com.ucsal.clinic.atendimento.client.ProntuarioClient;
import com.ucsal.clinic.atendimento.dto.AtendimentoRequest;
import com.ucsal.clinic.atendimento.dto.AtendimentoResponse;
import com.ucsal.clinic.atendimento.exception.ItemNaoEncontradoException;
import com.ucsal.clinic.atendimento.exception.RegraNegocioException;
import com.ucsal.clinic.atendimento.model.Atendimento;
import com.ucsal.clinic.atendimento.model.AtendimentoStatus;
import com.ucsal.clinic.atendimento.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoMapper atendimentoMapper;
    private final ProntuarioClient prontuarioClient;
    private final ProfissionalClient profissionalClient;
    private final MedicacaoClient medicacaoClient;

    public AtendimentoService(AtendimentoRepository atendimentoRepository,
                              AtendimentoMapper atendimentoMapper,
                              ProntuarioClient prontuarioClient,
                              ProfissionalClient profissionalClient,
                              MedicacaoClient medicacaoClient) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
        this.prontuarioClient = prontuarioClient;
        this.profissionalClient = profissionalClient;
        this.medicacaoClient = medicacaoClient;
    }

    @Transactional
    public AtendimentoResponse iniciarAtendimento(AtendimentoRequest request) {
        prontuarioClient.validarExistencia(request.prontuarioId());
        profissionalClient.validarExistencia(request.profissionalId());
        validarMedicacao(request);

        Atendimento atendimento = atendimentoMapper.toModel(request);
        return atendimentoMapper.toResponse(atendimentoRepository.save(atendimento));
    }

    @Transactional
    public AtendimentoResponse encerrarAtendimento(Long atendimentoId) {
        Atendimento atendimento = buscarEntidadePorId(atendimentoId);

        if (AtendimentoStatus.ENCERRADO.equals(atendimento.getStatus())) {
            throw new RegraNegocioException("Atendimento ja foi encerrado");
        }

        atendimento.setStatus(AtendimentoStatus.ENCERRADO);
        atendimento.setDataHoraEncerramento(LocalDateTime.now());
        return atendimentoMapper.toResponse(atendimentoRepository.save(atendimento));
    }

    @Transactional(readOnly = true)
    public AtendimentoResponse buscarPorId(Long atendimentoId) {
        return atendimentoMapper.toResponse(buscarEntidadePorId(atendimentoId));
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResponse> listarTodos() {
        return atendimentoRepository.findAll().stream()
                .map(atendimentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResponse> listarPorProntuario(Long prontuarioId) {
        return atendimentoRepository.findByProntuarioId(prontuarioId).stream()
                .map(atendimentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResponse> listarPorStatus(AtendimentoStatus status) {
        return atendimentoRepository.findByStatus(status).stream()
                .map(atendimentoMapper::toResponse)
                .toList();
    }

    private Atendimento buscarEntidadePorId(Long atendimentoId) {
        return atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new ItemNaoEncontradoException("Atendimento nao encontrado"));
    }

    private void validarMedicacao(AtendimentoRequest request) {
        if (request.medicacaoId() == null) {
            return;
        }

        if (request.quantidadeMedicacaoUtilizada() == null || request.quantidadeMedicacaoUtilizada() <= 0) {
            throw new RegraNegocioException("A quantidade de medicacao utilizada deve ser informada");
        }

        medicacaoClient.consumirEstoque(request.medicacaoId(), request.quantidadeMedicacaoUtilizada());
    }
}
