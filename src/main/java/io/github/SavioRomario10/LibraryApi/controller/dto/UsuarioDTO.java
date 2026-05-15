package io.github.SavioRomario10.LibraryApi.controller.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {

}