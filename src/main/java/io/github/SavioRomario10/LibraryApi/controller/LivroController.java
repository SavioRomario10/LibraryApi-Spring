package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.services.LivroService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.exception.RegistroDuplicadoException;
import io.github.SavioRomario10.LibraryApi.controller.dto.ErroResposta;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

  private final LivroService service;

  @PostMapping
  public ResponseEntity<Object> salvar(
    @RequestBody @Valid CadastroLivroDTO dto){

      try{
        return ResponseEntity.ok(dto);
      }
      catch(RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
      }
  }
}
