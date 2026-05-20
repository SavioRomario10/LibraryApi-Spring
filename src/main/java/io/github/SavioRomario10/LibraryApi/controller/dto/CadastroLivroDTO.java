package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CadastroLivroDTO(
  @ISBN(message = "ISBN inválido")
  @NotBlank(message = "ISBN é obrigatório")
  String isbn,
  @NotBlank(message = "Título é obrigatório")
  String titulo,
  @NotNull(message = "Data de publicação é obrigatória")
  @Past(message = "Data inválida")
  LocalDate dataPublicacao,
  GeneroLivro genero,
  BigDecimal preco,
  @NotNull(message = "Autor é obrigatório")
  UUID idAutor
) {

}