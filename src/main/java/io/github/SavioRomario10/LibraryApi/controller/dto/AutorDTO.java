package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.Autor;

public record AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {

  public Autor mapearParaAutor(){
    Autor autor = new Autor();

    autor.setNome(this.nome);
    autor.setDataNascimento(this.dataNascimento);
    autor.setNacionalidade(this.nacionalidade);

    return autor;
  }
}
