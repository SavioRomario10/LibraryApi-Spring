package io.github.SavioRomario10.LibraryApi.services;

import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;
import io.github.SavioRomario10.LibraryApi.validator.LivroValidator;

import static io.github.SavioRomario10.LibraryApi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

  private final LivroRepository repository;
  private final LivroValidator validator;

  public Livro salvar(Livro livro) {
    validator.validarLivro(livro);
    return repository.save(livro);
  }

  public Optional<Livro> obterPorId(UUID id) {
    return repository.findById(id);
  }

  public void deletar(Livro livro) {
    repository.delete(livro);
  }

  public Page<Livro> pesquisa(
    String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao, Integer pagina, Integer tamanhoPagina) {

    Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

    if(isbn != null)specs = specs.and(isbnEqual(isbn));
    if(titulo != null)specs = specs.and(tituloLike(titulo));
    if(genero != null)specs = specs.and(generoEqual(genero));
    if(anoPublicacao != null)specs = specs.and(anoPublicacaoEqual(anoPublicacao));
    if(nomeAutor != null)specs = specs.and(autorLike(nomeAutor));

    Pageable paginacao = PageRequest.of(pagina, tamanhoPagina);
    
    return repository.findAll(specs, paginacao);
  }

  public void atualizar(Livro livro){
    if(livro.getId() != null)
      throw new IllegalArgumentException("O id do autor precisa ser informado!");
    
    validator.validarLivro(livro);
    repository.save(livro);
  }
}
