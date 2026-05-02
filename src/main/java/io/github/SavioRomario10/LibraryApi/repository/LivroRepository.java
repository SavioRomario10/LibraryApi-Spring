package io.github.SavioRomario10.LibraryApi.repository;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Livro;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;


/**
 * @see LivroRepositoryTest
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

  //Query Method
  List<Livro> findByAutor(Autor autor);
  List<Livro> findByTitulo(String titulo);
  List<Livro> findByIsbn(String isbn);
  List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
  List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);
  List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

  //JPQL
  @Query("select l from Livro as l order by l.titulo")
  List<Livro> listarTodos();
  @Query("select a from Livro l join l.autor a ")
  List<Autor> listarAutores();
  @Query("select distinct l.titulo from Livro l")
  List<String> listarNomesDiferentes();
  @Query("""
    select l.genero
    from Livro l
    join l.autor a
    where a.nacionalidade like 'Brasil%'
    order by l.genero
    """)
  List<String> listarGenerosAutoresBrasileiros();
  @Query("select l from Livro l where l.genero = :genero order by :parametro")
  List<Livro> findByGenero(
    @Param("genero") GeneroLivro genero,
    @Param("parametro") String parametro
  );

  @Query("select l from Livro l where l.genero = ?1 order by ?2")
  List<Livro> findByGeneroQuery(GeneroLivro genero, String parametro);

  @Modifying
  @Transactional
  @Query("delete from Livro where genero = :genero")
  void deleteByGenero(@Param("genero") GeneroLivro genero);

  @Modifying
  @Transactional
  @Query("update Livro l set l.dataPublicacao = :novadata")
  void updateDataPublicacao(@Param("novadata") LocalDate novadata);
}
