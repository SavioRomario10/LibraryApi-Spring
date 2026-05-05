package io.github.SavioRomario10.LibraryApi.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import io.github.SavioRomario10.LibraryApi.model.Autor;

@Service
public class AutorService {

  private final AutorRepository repository;

  public AutorService(AutorRepository repository) {
    this.repository = repository;
  }

  public Autor salvar(Autor autor) {
    return repository.save(autor);
  }

  public Optional<Autor> obterPorId(UUID id){
    return repository.findById(id);
  }

  public void deletar(Autor autor){
    repository.delete(autor);
  }
}