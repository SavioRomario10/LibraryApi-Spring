package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
  String id,
  @NotBlank(message = "campo login é obrigatório")
  String login, 
  @NotBlank(message = "campo senha é obrigatório")
  String senha, 
  @NotBlank(message = "campo email é obrigatório")
  @Email (message = "email deve ser valido") 
  String email, 
  List<String> roles) {

}