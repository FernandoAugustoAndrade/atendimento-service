package com.ucsal.clinic.atendimento.repository;

import com.ucsal.clinic.atendimento.model.Atendimento;
import com.ucsal.clinic.atendimento.model.AtendimentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByProntuarioId(Long prontuarioId);

    List<Atendimento> findByStatus(AtendimentoStatus status);
}
