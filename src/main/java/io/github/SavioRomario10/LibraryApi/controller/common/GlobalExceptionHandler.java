package io.github.SavioRomario10.LibraryApi.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.SavioRomario10.LibraryApi.controller.dto.ErroCampo;
import io.github.SavioRomario10.LibraryApi.controller.dto.ErroResposta;

@RestControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
  public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
    
    List<FieldError> fieldErrors = e.getFieldErrors();

    List<ErroCampo> erros = 
      fieldErrors.stream().map(
          fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())
        ).collect(Collectors.toList());

    return new ErroResposta(
      HttpStatus.UNPROCESSABLE_CONTENT.value(),
      "Erro de validação",
      erros
    );
  }
}
