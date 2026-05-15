package io.github.SavioRomario10.LibraryApi.repository;

import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  
  Usuario findByLogin(String login);
}