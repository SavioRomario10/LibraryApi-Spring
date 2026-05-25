package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Schema(name = "CadastroLivro")
public record CadastroLivroDTO(
  @Schema(description = "ISBN do livro")
  @ISBN(message = "ISBN inválido")
  @NotBlank(message = "ISBN é obrigatório")
  String isbn,
  @Schema(description = "Título do livro")
  @NotBlank(message = "Título é obrigatório")
  String titulo,
  @Schema(description = "Data de publicação do livro")
  @NotNull(message = "Data de publicação é obrigatória")
  @Past(message = "Data inválida")
  LocalDate dataPublicacao,
  @Schema(description = "Gênero do livro")
  GeneroLivro genero,
  @Schema(description = "Preço do livro")
  BigDecimal preco,
  @Schema(description = "Identificador único do autor")
  @NotNull(message = "Autor é obrigatório")
  UUID idAutor
) {

}