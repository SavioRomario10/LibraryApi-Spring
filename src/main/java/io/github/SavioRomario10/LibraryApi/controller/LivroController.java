package io.github.SavioRomario10.LibraryApi.controller;

import io.github.SavioRomario10.LibraryApi.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

  private final LivroService service;
}
