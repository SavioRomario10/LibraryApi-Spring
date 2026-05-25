package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
  @NotBlank(message = "campo id é obrigatório")
  @Schema(description = "id do usuário")
  String id,
  @NotBlank(message = "campo login é obrigatório")
  @Schema(description = "login do usuário")
  String login, 
  @NotBlank(message = "campo senha é obrigatório")
  @Schema(description = "senha do usuário")
  String senha, 
  @NotBlank(message = "campo email é obrigatório")
  @Email (message = "email deve ser valido") 
  @Schema(description = "email do usuário")
  String email, 
  List<String> roles) {

}