package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.services.AutorService;
import jakarta.validation.Valid;
import io.github.SavioRomario10.LibraryApi.controller.mappers.AutorMapper;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
public class AutorController implements GenericController{

  private final AutorService service;
  private final AutorMapper mapper;

  @PostMapping
  @PreAuthorize("hasRole('GERENTE')")
  public ResponseEntity<Void> salvar(
    @RequestBody @Valid AutorDTO dto){

    Autor autor = mapper.toEntity(dto);
    service.salvar(autor);
    
    URI location = gerarHeaderLocation(autor.getId());
    
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){

    var idAutor = UUID.fromString(id);

    return service
      .obterPorId(idAutor)
      .map(autor -> {
        AutorDTO dto = mapper.toDto(autor);
        return ResponseEntity.ok(dto);
      }).orElseGet(() ->
      ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('GERENTE')")
  public ResponseEntity<Void> deletar(@PathVariable("id") String id){

    Optional<Autor> autorOptional = service.obterPorId(UUID.fromString(id));
    
    if(autorOptional.isPresent()){
      service.deletar(autorOptional.get());
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  public ResponseEntity<List<AutorDTO>> pesquisar(
    @RequestParam (value = "nome", required = false) String nome, 
    @RequestParam (value = "nacionalidade", required = false) String nacionalidade){

    List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);

    List<AutorDTO> autorDTOs = 
      autores.stream().map(mapper::toDto
        ).collect(Collectors.toList());

    return ResponseEntity.ok(autorDTOs);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('GERENTE')")
  public ResponseEntity<Void> atualizar(
    @PathVariable("id") String id,
    @RequestBody @Valid AutorDTO autorDTO){

      Optional<Autor> autorOptional = service.obterPorId(UUID.fromString(id));
      
      if(autorOptional.isEmpty()){
        return ResponseEntity.notFound().build();
      }
      
      Autor autor = autorOptional.get();
      autor.setNome(autorDTO.nome());
      autor.setDataNascimento(autorDTO.dataNascimento());
      autor.setNacionalidade(autorDTO.nacionalidade());
      
      service.atualizar(autor);
      
      return ResponseEntity.noContent().build();
  }
}