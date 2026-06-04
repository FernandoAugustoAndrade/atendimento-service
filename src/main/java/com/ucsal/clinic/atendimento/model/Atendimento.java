package com.ucsal.clinic.atendimento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prontuario_id", nullable = false)
    private Long prontuarioId;

    @Column(name = "profissional_id", nullable = false)
    private Long profissionalId;

    @Column(name = "medicacao_id")
    private Long medicacaoId;

    @Column(name = "quantidade_medicacao_utilizada")
    private Integer quantidadeMedicacaoUtilizada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtendimento tipoAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AtendimentoStatus status;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_encerramento")
    private LocalDateTime dataHoraEncerramento;

    @Column(columnDefinition = "TEXT")
    private String sintomas;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String medicaoDosagem;

    @Column(columnDefinition = "TEXT")
    private String tratamentoIndicado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProntuarioId() {
        return prontuarioId;
    }

    public void setProntuarioId(Long prontuarioId) {
        this.prontuarioId = prontuarioId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getMedicacaoId() {
        return medicacaoId;
    }

    public void setMedicacaoId(Long medicacaoId) {
        this.medicacaoId = medicacaoId;
    }

    public Integer getQuantidadeMedicacaoUtilizada() {
        return quantidadeMedicacaoUtilizada;
    }

    public void setQuantidadeMedicacaoUtilizada(Integer quantidadeMedicacaoUtilizada) {
        this.quantidadeMedicacaoUtilizada = quantidadeMedicacaoUtilizada;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public AtendimentoStatus getStatus() {
        return status;
    }

    public void setStatus(AtendimentoStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraEncerramento() {
        return dataHoraEncerramento;
    }

    public void setDataHoraEncerramento(LocalDateTime dataHoraEncerramento) {
        this.dataHoraEncerramento = dataHoraEncerramento;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMedicaoDosagem() {
        return medicaoDosagem;
    }

    public void setMedicaoDosagem(String medicaoDosagem) {
        this.medicaoDosagem = medicaoDosagem;
    }

    public String getTratamentoIndicado() {
        return tratamentoIndicado;
    }

    public void setTratamentoIndicado(String tratamentoIndicado) {
        this.tratamentoIndicado = tratamentoIndicado;
    }
}
