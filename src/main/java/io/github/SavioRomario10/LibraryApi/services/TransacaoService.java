package io.github.SavioRomario10.LibraryApi.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

  @Autowired
  private AutorRepository autorRepository;

  @Autowired
  private LivroRepository livroRepository;

  @Transactional
  public void atualizacaoSemAtualizar(){

    UUID id = Objects.requireNonNull(
      UUID.fromString("670e952f-7e3b-4165-b874-17416229e26f"));

    var livro = livroRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

    livro.setPreco(BigDecimal.valueOf(1000));
  }

  @Transactional
  public void execuatar(){
    Livro livro = new Livro();

    livro.setIsbn("564213265");
    livro.setTitulo("Livro novo");
    livro.setGenero(GeneroLivro.ROMANCE);
    livro.setDataPublicacao(LocalDate.of(1989,3,5));
    livro.setPreco(BigDecimal.valueOf(250));

    Autor autor = new Autor();
    autor.setNome("Francisca");
    autor.setDataNascimento(LocalDate.of(1959, 5, 2));
    autor.setNacionalidade("Brasileira");

    autorRepository.save(autor);

    livro.setAutor(autor);

    livroRepository.save(livro);

    if(autor.getNome().equals("Francisca")){
      throw new IllegalArgumentException("RollBack!");
    }
  }
}
