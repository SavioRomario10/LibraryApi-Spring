package io.github.SavioRomario10.LibraryApi.repository;

import java.util.List;
import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Livro;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

  //Query Method
  List<Livro> findByAutor(Autor autor);
  List<Livro> findByTitulo(String titulo);
  List<Livro> findByIsbn(String isbn);
}
