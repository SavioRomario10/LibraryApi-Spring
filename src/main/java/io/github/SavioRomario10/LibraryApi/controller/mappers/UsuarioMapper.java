package io.github.SavioRomario10.LibraryApi.controller.mappers;

import org.mapstruct.Mapper;

import io.github.SavioRomario10.LibraryApi.controller.dto.UsuarioDTO;
import io.github.SavioRomario10.LibraryApi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper{
  Usuario toEntity(UsuarioDTO dto);
}