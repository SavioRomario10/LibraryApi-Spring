package io.github.SavioRomario10.LibraryApi.security;

import io.github.SavioRomario10.LibraryApi.model.Usuario;
import io.github.SavioRomario10.LibraryApi.services.UsuarioService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityService {

  private final UsuarioService usuarioService;

  public Usuario obterUsuarioLogado(){
    
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication instanceof CustomAuthentication customAuthentication){
      return customAuthentication.getUsuario();
    }

    return null;
  }
}