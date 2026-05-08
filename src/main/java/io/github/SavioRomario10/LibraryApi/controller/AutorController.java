package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.controller.dto.ErroResposta;
import io.github.SavioRomario10.LibraryApi.exception.OperacaoNaoPermitidaException;
import io.github.SavioRomario10.LibraryApi.exception.RegistroDuplicadoException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
public class AutorController implements GenericController{

  private final AutorService service;
  private final AutorMapper mapper;

  @PostMapping
  public ResponseEntity<Object> salvar(
    @RequestBody @Valid AutorDTO dto){

    try{ 
      Autor autor = mapper.toEntity(dto);
      service.salvar(autor);
      
      URI location = gerarHeaderLocation(autor.getId());
      
      return ResponseEntity.created(location).build();
    }
    catch(RegistroDuplicadoException e){
      var erroDTO = ErroResposta.conflito(e.getMessage());
      return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
    }
  }

  @GetMapping("/{id}")
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
  public ResponseEntity<Object> deletar(@PathVariable("id") String id){
    try{

      Optional<Autor> autorOptional = service.obterPorId(UUID.fromString(id));
      
      if(autorOptional.isPresent()){
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.notFound().build();
    }
    catch(OperacaoNaoPermitidaException e){
      var erroResposta = ErroResposta.respostaPadrao(e.getMessage());

      return ResponseEntity.status(erroResposta.Status()).body(erroResposta);
    }
  }

  @GetMapping
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
  public ResponseEntity<Object> atualizar(
    @PathVariable("id") String id,
    @RequestBody @Valid AutorDTO autorDTO){

      try{
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
      catch(RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
      }
  }
}