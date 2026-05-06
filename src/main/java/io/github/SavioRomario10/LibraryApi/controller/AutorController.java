package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.controller.dto.ErroResposta;
import io.github.SavioRomario10.LibraryApi.exception.RegistroDuplicadoException;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.services.AutorService;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/autores")
public class AutorController {

  private final AutorService service;

  public AutorController(AutorService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){

    try{ 
      Autor autorEntidade = autor.mapearParaAutor();
      service.salvar(autorEntidade);
      
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();
      
      return ResponseEntity.created(location).build();
    }
    catch(RegistroDuplicadoException e){
      var erroDTO = ErroResposta.conflito(e.getMessage());
      return ResponseEntity.status(erroDTO.Status()).body(erroDTO);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){

    Optional<Autor> autorOptional = service.obterPorId(UUID.fromString(id));

    if (autorOptional.isPresent()) {
      Autor autor = autorOptional.get();
      AutorDTO autorDTO = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

      return ResponseEntity.ok(autorDTO);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable("id") String id){
    Optional<Autor> autorOptional = service.obterPorId(UUID.fromString(id));

    if(autorOptional.isPresent()){
      service.deletar(autorOptional.get());
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<AutorDTO>> pesquisar(
    @RequestParam (value = "nome", required = false) String nome, 
    @RequestParam (value = "nacionalidade", required = false) String nacionalidade){

    List<Autor> autores = service.pesquisa(nome, nacionalidade);
    List<AutorDTO> autorDTOs = 
      autores.stream().map(
        autor -> new AutorDTO(
          autor.getId(), 
          autor.getNome(), 
          autor.getDataNascimento(),
          autor.getNacionalidade())
        ).collect(Collectors.toList());

    return ResponseEntity.ok(autorDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> atualizar(
    @PathVariable("id") String id, @RequestBody AutorDTO autorDTO){

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