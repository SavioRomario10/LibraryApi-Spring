package io.github.SavioRomario10.LibraryApi.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import io.github.SavioRomario10.LibraryApi.model.Autor;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID>{

  List<Autor> findByNome(String nome);
  List<Autor> findByNacionalidade(String nacionalidade);
  List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

  Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);
}