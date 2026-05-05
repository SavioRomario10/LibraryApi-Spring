package io.github.SavioRomario10.LibraryApi.services;

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
}