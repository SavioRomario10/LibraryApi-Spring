package io.github.SavioRomario10.LibraryApi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper{
  
  @Mapping(target = "livros", ignore = true)
  @Mapping(target = "dataCadastro", ignore = true)
  @Mapping(target = "dataAtualizacao", ignore = true)
  @Mapping(target = "usuario", ignore = true)
  Autor toEntity(AutorDTO dto);

  AutorDTO toDto(Autor entity);
}
