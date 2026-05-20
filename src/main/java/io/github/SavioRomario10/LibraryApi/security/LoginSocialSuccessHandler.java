package io.github.SavioRomario10.LibraryApi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.SavioRomario10.LibraryApi.model.Usuario;
import io.github.SavioRomario10.LibraryApi.services.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

  private static final String SENHA_PADRAO = "123";
  
  private final UsuarioService service;

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request, 
    HttpServletResponse response, 
    Authentication authentication) throws ServletException, IOException {
    
    OAuth2AuthenticationToken auth2AuthenticationToken
      = (OAuth2AuthenticationToken) authentication;

    OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

    String email = oAuth2User.getAttribute("email");

    Usuario usuario = service.obterPorEmail(email);

    if(usuario == null){
      usuario = cadastrarUsuario(email);
    }

    authentication = new CustomAuthentication(usuario);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    super.onAuthenticationSuccess(request, response, authentication);
  }

  private Usuario cadastrarUsuario(String email){
    Usuario usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setLogin(obterLoginPorEmail(email));
    usuario.setSenha(SENHA_PADRAO);
    usuario.setRoles(List.of("OPERADOR"));

    service.salvar(usuario);
    return usuario;
  }

  private String obterLoginPorEmail(String email){
    return email.substring(0, email.indexOf("@"));
  }
}  