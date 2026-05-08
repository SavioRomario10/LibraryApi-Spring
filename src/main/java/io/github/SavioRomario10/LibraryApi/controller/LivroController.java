package io.github.SavioRomario10.LibraryApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.SavioRomario10.LibraryApi.services.LivroService;
import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.exception.RegistroDuplicadoException;
import io.github.SavioRomario10.LibraryApi.controller.dto.ErroResposta;
import io.github.SavioRomario10.LibraryApi.controller.mappers.LivroMapper;
import io.github.SavioRomario10.LibraryApi.model.Livro;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

  private final LivroService service;
  private final LivroMapper mapper;

  @PostMapping
  public ResponseEntity<Object> salvar(
    @RequestBody @Valid CadastroLivroDTO dto){

      try{
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);

        URI location = gerarHeaderLocation(livro.getId());
        
        return ResponseEntity.created(location).build();
      }
      catch(RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
      }
  }
}
