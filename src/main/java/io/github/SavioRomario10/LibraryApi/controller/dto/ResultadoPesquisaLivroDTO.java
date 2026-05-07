package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;

public record ResultadoPesquisaLivroDTO(
  UUID id,
  String isbn,
  String titulo,
  LocalDate dataPublicacao,
  GeneroLivro genero,
  BigDecimal preco,
  AutorDTO autor
) {

}
