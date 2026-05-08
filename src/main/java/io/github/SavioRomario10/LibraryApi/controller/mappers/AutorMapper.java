package io.github.SavioRomario10.LibraryApi.controller.mappers;

import org.mapstruct.Mapper;

import io.github.SavioRomario10.LibraryApi.controller.dto.AutorDTO;
import io.github.SavioRomario10.LibraryApi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

  Autor toEntity(AutorDTO dto);

  AutorDTO toDto(Autor entity);
}
