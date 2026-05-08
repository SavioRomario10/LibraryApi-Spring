package io.github.SavioRomario10.LibraryApi.services;

import io.github.SavioRomario10.LibraryApi.model.Livro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;

@Service
@RequiredArgsConstructor
public class LivroService {

  private final LivroRepository repository;

  public Livro salvar(Livro livro) {
    return repository.save(livro);
  }
}
