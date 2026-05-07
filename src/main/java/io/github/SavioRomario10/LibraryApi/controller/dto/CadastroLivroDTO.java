package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;

public record CadastroLivroDTO(
  String isbn,
  String titulo,
  LocalDate dataPublicacao,
  GeneroLivro genero,
  BigDecimal preco,
  UUID idAutor
) {

}
