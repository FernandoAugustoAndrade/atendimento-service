package com.ucsal.clinic.atendimento.exception;

import com.ucsal.clinic.atendimento.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ItemNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarItemNaoEncontrado(ItemNaoEncontradoException exception) {
        return criarResposta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(RegraNegocioException exception) {
        return criarResposta(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IntegracaoException.class)
    public ResponseEntity<ErroResponse> tratarIntegracao(IntegracaoException exception) {
        return criarResposta(HttpStatus.BAD_GATEWAY, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Dados invalidos");

        return criarResposta(HttpStatus.BAD_REQUEST, mensagem);
    }

    private ResponseEntity<ErroResponse> criarResposta(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(new ErroResponse(
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                LocalDateTime.now()
        ));
    }
}
