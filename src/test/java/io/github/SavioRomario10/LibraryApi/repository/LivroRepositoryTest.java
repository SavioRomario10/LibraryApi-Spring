package io.github.SavioRomario10.LibraryApi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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

    Autor autor = autorRepository.findById(UUID.fromString("d2f4ce19-6607-44ef-ba96-6705e0424b63")).orElse(null);

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
}
