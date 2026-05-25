package io.github.SavioRomario10.LibraryApi.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Objects;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.services.AutorService;
import io.github.SavioRomario10.LibraryApi.controller.mappers.AutorMapper;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
@Tag(name = "Autores")
public class AutorController implements GenericController{

  private final AutorService service;
  private final AutorMapper mapper;

  @PostMapping()
  @PreAuthorize("hasRole('GERENTE')")
  @Operation(summary = "Salvar", description = "Salva um novo autor no sistema.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso."),
    @ApiResponse(responseCode = "422", description = "Erro de validação."),
    @ApiResponse(responseCode = "409", description = "Requisição inválida.")
  })
  public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto){
    
    Autor autor = mapper.toEntity(dto);
    service.salvar(autor);
    
    URI location = gerarHeaderLocation(autor.getId());

    if(location == null){
      return ResponseEntity.badRequest().build();
    }
    
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  @Operation(summary = "Obter detalhes", description = "Obtém os detalhes de um autor pelo ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso."),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
  })
  public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){

    var idAutor = Objects.requireNonNull(UUID.fromString(id));

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
  @Operation(summary = "Deletar", description = "Deleta um autor do sistema.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso."),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
  })
  public ResponseEntity<Object> deletar(@PathVariable("id") String id){

    Optional<Autor> autorOptional = service.obterPorId(Objects.requireNonNull(UUID.fromString(id)));
    
    
    return autorOptional.map(autor -> {
        service.deletar(Objects.requireNonNull(autor));
        return ResponseEntity.noContent().build();
        }).orElseGet(() ->
          ResponseEntity.notFound().build()
        );
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
  @Operation(summary = "Pesquisar", description = "Pesquisa autores com base em critérios.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Autores encontrados com sucesso."),
    @ApiResponse(responseCode = "404", description = "Autores não encontrados.")
  })
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
  @Operation(summary = "Atualizar", description = "Atualiza os detalhes de um autor existente.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Autor atualizado com sucesso."),
    @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
    @ApiResponse(responseCode = "422", description = "Erro de validação.")
  })
  public ResponseEntity<Void> atualizar(
    @PathVariable("id") String id,
    @RequestBody @Valid AutorDTO autorDTO){

      Optional<Autor> autorOptional = service.obterPorId(Objects.requireNonNull(UUID.fromString(id)));
      
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