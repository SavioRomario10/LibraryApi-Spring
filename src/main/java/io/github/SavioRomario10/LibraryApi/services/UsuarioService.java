package io.github.SavioRomario10.LibraryApi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.model.Usuario;
import io.github.SavioRomario10.LibraryApi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository repository;
  private final PasswordEncoder encoder;

  public void salvar(Usuario usuario){
    var senha = usuario.getSenha();
    usuario.setSenha(encoder.encode(senha));

    repository.save(usuario);
  }

  public Usuario obterPorLogin(String login){
    return repository.findByLogin(login);
  }
}