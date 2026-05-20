package io.github.SavioRomario10.LibraryApi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.lang.NonNull;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;
import io.github.SavioRomario10.LibraryApi.security.SecurityService;
import io.github.SavioRomario10.LibraryApi.validator.AutorValidador;
import lombok.RequiredArgsConstructor;
import io.github.SavioRomario10.LibraryApi.exceptions.OperacaoNaoPermitidaException;
import io.github.SavioRomario10.LibraryApi.model.Autor;
import io.github.SavioRomario10.LibraryApi.model.Usuario;

@Service
@RequiredArgsConstructor
public class AutorService {

  private final AutorValidador validador;
  private final AutorRepository repository;
  private final LivroRepository livroRepository;
  private final SecurityService securityService;

  public Autor salvar(Autor autor) {
    validador.validar(autor);
    
    Usuario usuario = securityService.obterUsuarioLogado();
    autor.setUsuario(usuario);

    return repository.save(autor);
  }

  public void atualizar(Autor autor) {
    if(autor.getId() == null) 
      throw new IllegalArgumentException("O id do autor precisa ser informado!");
    
    validador.validar(autor);
    repository.save(autor);
  }

  public Optional<Autor> obterPorId(@NonNull UUID id){
    return repository.findById(id);
  }

  public void deletar(@NonNull Autor autor){
    if(possuiLivro(autor)){
      throw new OperacaoNaoPermitidaException("Não é permitido excluir, este Autor possui livros cadastrados!");
    }
    repository.delete(autor);
  }

  public List<Autor> pesquisa(String nome, String nacionalidade){
    if(nome != null && nacionalidade != null){
      return repository.findByNomeAndNacionalidade(nome, nacionalidade);
    }
    if(nome != null){
      return repository.findByNome(nome);
    }
    if(nacionalidade != null){
      return repository.findByNacionalidade(nacionalidade);
    }
    return repository.findAll();
  }

  public List<Autor> pesquisaByExample(String nome, String nacionalidade){
    var autor = new Autor();

    autor.setNome(nome);
    autor.setNacionalidade(nacionalidade);

    ExampleMatcher matcher = ExampleMatcher
      .matching()
      .withIgnoreCase()
      .withIgnoreNullValues()
      .withStringMatcher(StringMatcher.CONTAINING);
    Example<Autor> autorExample = Example.of(autor, matcher);

    return repository.findAll(autorExample);
  }

  public boolean possuiLivro(Autor autor){
    return livroRepository.existsByAutor(autor);
  }
}