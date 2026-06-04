package com.ucsal.clinic.atendimento;

import com.ucsal.clinic.atendimento.dto.AtendimentoRequest;
import com.ucsal.clinic.atendimento.dto.AtendimentoResponse;
import com.ucsal.clinic.atendimento.model.AtendimentoStatus;
import com.ucsal.clinic.atendimento.service.AtendimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @PostMapping
    public ResponseEntity<AtendimentoResponse> iniciarAtendimento(@RequestBody @Valid AtendimentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoService.iniciarAtendimento(request));
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<AtendimentoResponse> encerrarAtendimento(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.encerrarAtendimento(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoResponse>> listar(@RequestParam(required = false) AtendimentoStatus status) {
        if (status == null) {
            return ResponseEntity.ok(atendimentoService.listarTodos());
        }

        return ResponseEntity.ok(atendimentoService.listarPorStatus(status));
    }

    @GetMapping("/prontuario/{prontuarioId}")
    public ResponseEntity<List<AtendimentoResponse>> listarPorProntuario(@PathVariable Long prontuarioId) {
        return ResponseEntity.ok(atendimentoService.listarPorProntuario(prontuarioId));
    }
}
