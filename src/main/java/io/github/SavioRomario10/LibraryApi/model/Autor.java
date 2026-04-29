package io.github.SavioRomario10.LibraryApi.model;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="autor")
@Getter
@Setter
public class Autor {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  @Column(name = "data_nascimento", nullable = false)
  private LocalDate dataNascimento;

  @Column(name = "nacionalidade", length = 50, nullable = false)
  private String nacionalidade;

  @OneToMany(mappedBy = "autor")
  private List<Livro> livros;

  @Override
  public String toString() {
    return "Autor [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", nacionalidade="
        + nacionalidade + "]";
  }  
}