package io.github.SavioRomario10.LibraryApi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.repository.AutorRepository;
import io.github.SavioRomario10.LibraryApi.validator.AutorValidador;
import io.github.SavioRomario10.LibraryApi.model.Autor;

@Service
public class AutorService {

  private final AutorRepository repository;
  private final AutorValidador validador;

  public AutorService(AutorRepository repository, AutorValidador validador) {
    this.repository = repository;
    this.validador = validador;
  }

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
}