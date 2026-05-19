package io.github.SavioRomario10.LibraryApi.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.SavioRomario10.LibraryApi.model.Usuario;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomAuthentication implements Authentication {

  private final Usuario usuario;

  public Usuario getUsuario() {
    return usuario;
  }
  public Collection<GrantedAuthority> getAuthorities() {
    return this.usuario.getRoles()
      .stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }
  @Override
  public String getName() {
    return usuario.getLogin();
  }
  @Override
  public Object getPrincipal() {
    return usuario;
  }
  @Override
  public Object getCredentials() {
    return null;
  }
  @Override
  public Object getDetails() {
    return usuario;
  }
  @Override
  public boolean isAuthenticated() {
    return true;
  }
  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
  }
}
