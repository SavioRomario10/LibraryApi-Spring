package io.github.SavioRomario10.LibraryApi.repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;    

import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;

@SpringBootTest
public class AutorRepositoryTest {

  @Autowired
  AutorRepository repository;

  @Autowired
  LivroRepository livroRepository;

  @Test
  public void salvarTest(){

    Autor autor = new Autor();

    autor.setNome("Sávio Romário");
    autor.setDataNascimento(LocalDate.of(1996, 11, 03));
    autor.setNacionalidade("Brasileiro");

    var autorSalvo = repository.save(autor);
    System.out.println("Autor salvo: " + autorSalvo);
  }

  @Test
  public void atualizarTest(){
    var id = UUID.fromString("d2f4ce19-6607-44ef-ba96-6705e0424b63");

    Optional<Autor> possivelAutor = repository.findById(id);

    if(possivelAutor.isPresent()){
      Autor autorEncontrado = possivelAutor.get();
      
      autorEncontrado.setNome("Sávio Romário");

      repository.save(autorEncontrado);
    }
  }

  @Test
  public void listarTest(){
    List<Autor> list = repository.findAll();
    list.forEach(System.out::println);
  }

  @Test
  public void countTest(){
    var count = repository.count();
    System.out.println("Contagem de autores: " + count);
  }

  @Test
  public void deletePorIdTest(){
    var id = UUID.fromString("d2f4ce19-6607-44ef-ba96-6705e0424b63");

    repository.deleteById(id);
  }

  @Test
  public void deletarTest(){
    var id = UUID.fromString("145f5905-4234-4cc5-a30e-949a1e7ae22b");
    var autor = repository.findById(id).get();

    repository.delete(autor);
  }

  @Test
  public void salvarAutorComLivrosTest(){

    Autor autor = new Autor();

    autor.setNome("Antonio");
    autor.setDataNascimento(LocalDate.of(1970, 8, 03));
    autor.setNacionalidade("Brasileiro");

    Livro livro = new Livro();

    livro.setIsbn("1465871234");
    livro.setTitulo("Matematica para iniciantes");
    livro.setGenero(GeneroLivro.CIENCIA);
    livro.setDataPublicacao(LocalDate.of(1995,1, 2));
    livro.setPreco(BigDecimal.valueOf(100));
    livro.setAutor(autor);

    autor.setLivros(new ArrayList<>());
    autor.getLivros().add(livro);

    repository.save(autor);
    livroRepository.saveAll(autor.getLivros());
  }

  @Test
  public void mostrarLivrosAutor(){
    var id = UUID.fromString("48fe489c-1dcc-4d54-888f-7eacd9a4d207");
    var autor = repository.findById(id).get();

    List<Livro> livros = livroRepository.findByAutor(autor);
    autor.setLivros(livros);

    autor.getLivros().forEach(System.out::println);
  }
}