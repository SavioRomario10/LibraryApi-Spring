package io.github.SavioRomario10.LibraryApi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

  @Autowired
  AutorRepository autorRepository;
  
  @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
  public abstract Livro toEntity(CadastroLivroDTO dto);
}
