package io.github.SavioRomario10.LibraryApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.github.SavioRomario10.LibraryApi.controller.mappers.LivroMapper;
import io.github.SavioRomario10.LibraryApi.services.LivroService;
import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;

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

  @GetMapping("/{id}")
  public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes( @PathVariable("id") String id){
    return service.obterPorId(UUID.fromString(id))
      .map(livro -> {
        var dto = mapper.toDTO(livro);
        return ResponseEntity.ok(dto);
      }).orElseGet(
        () -> ResponseEntity.notFound().build()
      );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deletar(
    @PathVariable("id") String id){
      return service.obterPorId(UUID.fromString(id))
          .map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
          }).orElseGet(
            () -> ResponseEntity.notFound().build()
          );
  }

  @GetMapping
  public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
    @RequestParam(value = "isbn", required = false) String isbn,
    @RequestParam(value = "titulo", required = false) String titulo,
    @RequestParam(value = "genero", required = false) GeneroLivro genero,
    @RequestParam(value = "nome-autor", required = false) String nomeAutor,
    @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao
  ){

    var resultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);

    var list = resultado
      .stream().map(mapper::toDTO)
      .collect(Collectors.toList());

    return ResponseEntity.ok(list);
  }
}
