package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Autor")
public record AutorDTO(
  @Schema(description = "Identificador único do autor")
  UUID id,
  @NotBlank(message = "Campo nome é obrigatório")
  @Size(min = 3, max = 100, message = "campo fora do padrão")
  @Schema(description = "Nome do autor")
  String nome,
  @NotNull(message = "Campo data de nascimento é obrigatório")
  @Past(message = "Data inválida")
  @Schema(description = "Data de nascimento do autor")
  LocalDate dataNascimento,
  @NotBlank(message = "Campo nacionalidade é obrigatório")
  @Size(min = 3, max = 50, message = "campo fora do padrão")
  @Schema(description = "Nacionalidade do autor")
  String nacionalidade
){
}
