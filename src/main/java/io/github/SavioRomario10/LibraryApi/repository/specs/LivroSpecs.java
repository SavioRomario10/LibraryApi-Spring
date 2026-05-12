package io.github.SavioRomario10.LibraryApi.repository.specs;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import io.github.SavioRomario10.LibraryApi.model.Livro;
import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import jakarta.persistence.criteria.JoinType;

public class LivroSpecs {

  public static Specification<Livro> isbnEqual(String isbn) {
    return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
  }

  public static Specification<Livro> tituloLike(String titulo) {
    return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
  }

  public static Specification<Livro> generoEqual(GeneroLivro genero){
    return (root, query, cb) -> cb.equal(root.get("genero"), genero);
  }

  public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
    return (root, query, cb) -> 
      cb.equal(cb.function("to_char",String.class,
       root.get("dataPublicacao"), cb.literal("YYYY")), 
       anoPublicacao.toString());
  }

  public static Specification<Livro> autorLike(String autor){
    return (root, query, cb) -> {
      Join<Object,Object> join = root.join("autor", JoinType.LEFT);

      return cb.like(cb.upper(join.get("nome")), "%" + autor.toUpperCase() + "%");
    };
  }
}
