package io.github.SavioRomario10.LibraryApi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.Objects;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Livro;

@SpringBootTest
public class LivroRepositoryTest {
  
  @Autowired
  LivroRepository repository;

  @Autowired
  AutorRepository autorRepository;

  @Test
  public void salvarTest(){
    Livro livro = new Livro();

    livro.setIsbn("2312654653");
    livro.setTitulo("Dias de um futuro esquecido");
    livro.setGenero(GeneroLivro.FANTASIA);
    livro.setDataPublicacao(LocalDate.of(2015, 5, 5));
    livro.setPreco(BigDecimal.valueOf(150));

    Autor autor = autorRepository.findById(
        Objects.requireNonNull(
            UUID.fromString(
              "670e952f-7e3b-4165-b874-17416229e26f")
            )).orElse(null);

    livro.setAutor(autor);

    repository.save(livro);
  }

  @Test
  public void salvarCascadeTest(){
    Livro livro = new Livro();

    livro.setIsbn("123456789");
    livro.setTitulo("UFO");
    livro.setGenero(GeneroLivro.FICCAO);
    livro.setDataPublicacao(LocalDate.of(1980, 1, 2));
    livro.setPreco(BigDecimal.valueOf(100));

    Autor autor = new Autor();
    autor.setNome("Lorena");
    autor.setDataNascimento(LocalDate.of(2021, 10, 28));
    autor.setNacionalidade("Brasileira");

    autorRepository.save(autor);

    livro.setAutor(autor);

    repository.save(livro);
  }

  @Test
  public void atualizarTest(){
    var id =  Objects.requireNonNull(
      UUID.fromString("59c69b00-0f34-4ef1-a6c1-94c1ee5ab2f6"));

    Optional<Livro> possivelLivro = repository.findById(id);

    if(possivelLivro.isPresent()){
      Livro livroEncontrado = possivelLivro.get();
      
      livroEncontrado.setTitulo("UFO: uma história inventada");

      repository.save(livroEncontrado);
    }
  }

  @Test
  public void listarTest(){
    List<Livro> list = repository.findAll();
    list.forEach(System.out::println);
  }

@Test
  public void countTest(){
    var count = repository.count();
    System.out.println("Contagem de livros: " + count);
  }

  @Test
  public void deletePorIdTest(){
    var id =  Objects.requireNonNull(
      UUID.fromString("670e952f-7e3b-4165-b874-17416229e26f"));

    repository.deleteById(id);
  }

  @Test
  public void deletarTest(){
    var id =  Objects.requireNonNull(
      UUID.fromString("670e952f-7e3b-4165-b874-17416229e26f"));

    var livro = repository.findById(id).get();

    if(livro != null){
      repository.delete(livro);
    }
  }

  @Test
  @Transactional
  public void buscarLivroTest(){
    UUID id = Objects.requireNonNull(
      UUID.fromString("670e952f-7e3b-4165-b874-17416229e26f"));

    Livro livro = repository.findById(id).orElse(null);

    System.out.println("livro:"+livro.getTitulo());
    System.out.println("Autor:"+livro.getAutor().getNome());
  }

  @Test
  public void buscarPorTituloTest(){
    List<Livro> livros = repository.findByTitulo("UFO");
    livros.forEach(System.out::println);
  }

  @Test
  public void buscarPorIsbnTest(){
    Optional<Livro> livro = repository.findByIsbn("1465871234");
    livro.ifPresent(System.out::println); 
  }

  @Test
  public void buscarPorTituloAndPrecoTest(){
    var preco = BigDecimal.valueOf(100);
    String titulo = "UFO";
    List<Livro> livros = repository.findByTituloAndPreco(titulo, preco);
    livros.forEach(System.out::println);
  }

  @Test
  public void listarLivrosComQuery(){
    List<Livro> resultado = repository.listarTodos();
    resultado.forEach(System.out::println);
  }

  @Test
  public void listarAutores(){
    List<Autor> autores = repository.listarAutores();
    autores.forEach(System.out::println);
  }

  @Test
  public void listarNomesDiferentes(){
    List<String> nomes = repository.listarNomesDiferentes();
    nomes.forEach(System.out::println);
  }

  @Test
  public void listarGenerosAutoresBrasileiros(){
    List<String> generos = repository.listarGenerosAutoresBrasileiros();
    generos.forEach(System.out::println);
  }

  @Test
  public void listarPorGenero(){
    var resultado = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");

    resultado.forEach(System.out::println);
  }

  @Test
  public void listarPorGeneroQuery(){
    var resultado = repository.findByGeneroQuery(GeneroLivro.FICCAO, "dataPublicacao");

    resultado.forEach(System.out::println);
  }

  @Test
  public void deletePorGeneroTest(){
    repository.deleteByGenero(GeneroLivro.CIENCIA);
  }

  @Test
  public void updateDataPublicacaoTest(){
    repository.updateDataPublicacao(LocalDate.of(2026, 12, 20));
  }
}
