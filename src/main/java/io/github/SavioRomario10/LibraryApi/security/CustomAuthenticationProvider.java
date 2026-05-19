package io.github.SavioRomario10.LibraryApi.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.github.SavioRomario10.LibraryApi.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider{

  private final UsuarioService service;
  private final PasswordEncoder encoder;

  @Override
  public Authentication authenticate(Authentication authentication) {
    
    String login = authentication.getName();
    String password = authentication.getCredentials().toString();

    var usuario = service.obterPorLogin(login);

    if(usuario == null){
      throw newUsernameNotFoundException();
    }

    String senhaHash = usuario.getSenha();

    boolean senhaValida = encoder.matches(password, senhaHash);

    if(senhaValida){
      return new CustomAuthentication(usuario);
    }

    throw newUsernameNotFoundException();
  }

  private UsernameNotFoundException newUsernameNotFoundException() {
    return new UsernameNotFoundException("usuario ou senha incorretos");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
  }
}