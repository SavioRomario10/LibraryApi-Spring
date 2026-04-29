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
import lombok.Data;

@Entity
@Table(name="autor")
@Data
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

  @OneToMany(mappedBy = "idAutor")
  private List<Livro> livros;
}