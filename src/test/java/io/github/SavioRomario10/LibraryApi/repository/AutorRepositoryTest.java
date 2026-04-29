package io.github.SavioRomario10.LibraryApi.repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;          

import io.github.SavioRomario10.LibraryApi.model.Autor;

@SpringBootTest
public class AutorRepositoryTest {

  @Autowired
  AutorRepository repository;

  @Test
  public void salvarTest(){

    Autor autor = new Autor();

    autor.setNome("Gaby Marques");
    autor.setDataNascimento(LocalDate.of(2000, 10, 25));
    autor.setNacionalidade("Brasileira");

    var autorSalvo = repository.save(autor);
    System.out.println("Autor salvo: " + autorSalvo);
  }

  @Test
  public void atualizarTest(){
    var id = UUID.fromString("64ca133d-6d3c-42bc-809b-78aa37729292");

    Optional<Autor> possivelAutor = repository.findById(id);

    if(possivelAutor.isPresent()){
      Autor autorEncontrado = possivelAutor.get();
      
      autorEncontrado.setNome("Sávio");

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
    System.out.println("Contagem de autores: " + repository.count());
  }
}