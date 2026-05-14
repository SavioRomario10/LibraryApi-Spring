package io.github.SavioRomario10.LibraryApi.validator;

import java.util.Optional;

import io.github.SavioRomario10.LibraryApi.exceptions.CampoInvalidoException;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import io.github.SavioRomario10.LibraryApi.exceptions.RegistroDuplicadoException;
import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;

@Component
@RequiredArgsConstructor
public class LivroValidator {

  private static final int ANO_EXIGENCIA_PRECO = 2020;

  private final LivroRepository repository;

  public void validarLivro(Livro livro) {
    if(existeLivroComIsbn(livro)){
      throw new RegistroDuplicadoException("Já existe um livro com esse ISBN");
    }

    if(isPrecoObrigatorioNulo(livro)){
      throw new CampoInvalidoException("preco", "O preço do livro precisa ser informado!");
    }
  }

  private boolean existeLivroComIsbn(Livro livro){
   Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

   if(livro.getId() == null) return livroEncontrado.isPresent();

   return livroEncontrado
            .map(Livro::getId)
            .stream()
            .anyMatch(id -> !id.equals(livro.getId()));
  };

  private boolean isPrecoObrigatorioNulo(Livro livro){
    return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
  }
}