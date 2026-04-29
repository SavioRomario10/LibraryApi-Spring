package io.github.SavioRomario10.LibraryApi.repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;

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

    autor.setNome("Sávio Romário");
    autor.setDataNascimento(LocalDate.of(1996, 11, 3));
    autor.setNacionalidade("Brasileiro");

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
}