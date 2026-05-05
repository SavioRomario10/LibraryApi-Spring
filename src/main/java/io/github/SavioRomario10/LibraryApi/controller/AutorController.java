package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.services.AutorService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.UUID;
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
  public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){

    Autor autorEntidade = autor.mapearParaAutor();
    service.salvar(autorEntidade);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();

    return ResponseEntity.created(location).build();
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
}