package io.github.SavioRomario10.LibraryApi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.SavioRomario10.LibraryApi.controller.dto.CadastroLivroDTO;
import io.github.SavioRomario10.LibraryApi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper{

  @Autowired
  AutorRepository autorRepository;
  
  @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dataCadastro", ignore = true)
  @Mapping(target = "dataAtualizacao", ignore = true)
  @Mapping(target = "usuario", ignore = true)
  public abstract Livro toEntity(CadastroLivroDTO dto);

  public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
