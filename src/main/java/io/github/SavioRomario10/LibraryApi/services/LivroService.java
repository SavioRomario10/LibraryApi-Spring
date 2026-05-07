package io.github.SavioRomario10.LibraryApi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;

@Service
@RequiredArgsConstructor
public class LivroService {

  private final LivroRepository repository;
}
