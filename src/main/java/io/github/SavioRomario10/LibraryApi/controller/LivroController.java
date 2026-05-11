package io.github.SavioRomario10.LibraryApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;

import io.github.SavioRomario10.LibraryApi.services.LivroService;
import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.controller.mappers.LivroMapper;
import io.github.SavioRomario10.LibraryApi.model.Livro;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

  private final LivroService service;
  private final LivroMapper mapper;

  @PostMapping
  public ResponseEntity<Void> salvar(
    @RequestBody @Valid CadastroLivroDTO dto){

      Livro livro = mapper.toEntity(dto);
      service.salvar(livro);
      URI location = gerarHeaderLocation(livro.getId());
      
      return ResponseEntity.created(location).build();
  }
}
