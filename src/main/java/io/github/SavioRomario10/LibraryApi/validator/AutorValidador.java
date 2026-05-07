package io.github.SavioRomario10.LibraryApi.validator;

import java.util.Optional;

import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import io.github.SavioRomario10.LibraryApi.exception.RegistroDuplicadoException;
import io.github.SavioRomario10.LibraryApi.model.Autor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutorValidador {

  private final AutorRepository repository;

  public void validar(Autor autor){
    if(existeAutorCadastrado(autor)){
      throw new RegistroDuplicadoException("Autor já cadastrado");
    }
  }

  private boolean existeAutorCadastrado(Autor autor){
    Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
      autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

    if(autor.getId() == null){
      return autorEncontrado.isPresent();
    }

    return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
  }
}
