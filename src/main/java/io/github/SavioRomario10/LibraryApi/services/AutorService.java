package io.github.SavioRomario10.LibraryApi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import io.github.SavioRomario10.LibraryApi.repository.LivroRepository;
import io.github.SavioRomario10.LibraryApi.validator.AutorValidador;
import lombok.RequiredArgsConstructor;
import io.github.SavioRomario10.LibraryApi.exception.OperacaoNaoPermitidaException;
import io.github.SavioRomario10.LibraryApi.model.Autor;

@Service
@RequiredArgsConstructor
public class AutorService {

  private final AutorRepository repository;
  private final AutorValidador validador;
  private final LivroRepository livroRepository;

  public Autor salvar(Autor autor) {
    validador.validar(autor);
    return repository.save(autor);
  }

  public void atualizar(Autor autor) {
    if(autor.getId() == null) 
      throw new IllegalArgumentException("O id do autor precisa ser informado!");
    
    validador.validar(autor);
    repository.save(autor);
  }

  public Optional<Autor> obterPorId(UUID id){
    return repository.findById(id);
  }

  public void deletar(Autor autor){
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

  public boolean possuiLivro(Autor autor){
    return livroRepository.existsByAutor(autor);
  }
}