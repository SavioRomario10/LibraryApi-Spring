package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AutorDTO(
  UUID id,
  @NotBlank(message = "Campo nome é obrigatório")
  @Size(min = 3, max = 100, message = "campo fora do padrão")
  String nome,
  @NotNull(message = "Campo data de nascimento é obrigatório")
  @Past(message = "Data inválida")
  LocalDate dataNascimento,
  @NotBlank(message = "Campo nacionalidade é obrigatório")
  @Size(min = 3, max = 50, message = "campo fora do padrão")
  String nacionalidade
){

  public Autor mapearParaAutor(){
    Autor autor = new Autor();

    autor.setNome(this.nome);
    autor.setDataNascimento(this.dataNascimento);
    autor.setNacionalidade(this.nacionalidade);

    return autor;
  }
}
