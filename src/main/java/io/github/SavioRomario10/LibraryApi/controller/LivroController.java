package io.github.SavioRomario10.LibraryApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;

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
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  public ResponseEntity<Void> salvar(
    @RequestBody @Valid CadastroLivroDTO dto){

      Livro livro = mapper.toEntity(dto);
      service.salvar(livro);
      URI location = gerarHeaderLocation(livro.getId());
      
      if(location == null){
        return ResponseEntity.badRequest().build();
      }

      return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
    @RequestParam(value = "isbn", required = false) String isbn,
    @RequestParam(value = "titulo", required = false) String titulo,
    @RequestParam(value = "genero", required = false) GeneroLivro genero,
    @RequestParam(value = "nome-autor", required = false) String nomeAutor,
    @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
    @RequestParam(value = "pagina", defaultValue = "0") 
    Integer pagina,
    @RequestParam(value = "tamanho-pagina", defaultValue = "10")
    Integer tamanhoPagina
  ){

    Page<Livro> paginaResultado = service.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

    Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

    return ResponseEntity.ok(resultado);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  public ResponseEntity<Object> atualizar(
    @PathVariable("id") String id, 
    @RequestBody @Valid CadastroLivroDTO dto){
    
    return service.obterPorId(UUID.fromString(id))
      .map(livro -> {
       Livro entidade = mapper.toEntity(dto);

       livro.setDataPublicacao(entidade.getDataPublicacao());
       livro.setGenero(entidade.getGenero());
       livro.setIsbn(entidade.getIsbn());
       livro.setPreco(entidade.getPreco());
       livro.setTitulo(entidade.getTitulo());
       livro.setAutor(entidade.getAutor());

       service.atualizar(livro);

       return ResponseEntity.noContent().build();

      }).orElseGet(
        () -> ResponseEntity.notFound().build()
      );
  }
}