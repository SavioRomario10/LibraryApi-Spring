package io.github.SavioRomario10.LibraryApi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.github.SavioRomario10.LibraryApi.model.enums.GeneroLivro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "isbn", length = 20, nullable = false)
  private String isbn;

  @Column(name = "titulo", length = 150, nullable = false)
  private String titulo;

  @Enumerated(EnumType.STRING)
  @Column(name = "genero", length = 30, nullable = false)
  private GeneroLivro genero;

  @Column(name = "data_publicacao")
  private LocalDate dataPublicacao;

  @Column(name = "preco", precision = 18, scale = 2)
  private BigDecimal preco;

  @ManyToOne
  @JoinColumn(name = "id_autor")
  private Autor autor;

  public Livro() {}
  public Livro(UUID id, String isbn, String titulo, GeneroLivro genero, LocalDate dataPublicacao, BigDecimal preco,
      Autor autor) {
    this.id = id;
    this.isbn = isbn;
    this.titulo = titulo;
    this.genero = genero;
    this.dataPublicacao = dataPublicacao;
    this.preco = preco;
    this.autor = autor;
  }

  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }
  public String getIsbn() {
    return isbn;
  }
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }
  public String getTitulo() {
    return titulo;
  }
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }
  public GeneroLivro getGenero() {
    return genero;
  }
  public void setGenero(GeneroLivro genero) {
    this.genero = genero;
  }
  public LocalDate getDataPublicacao() {
    return dataPublicacao;
  }
  public void setDataPublicacao(LocalDate dataPublicacao) {
    this.dataPublicacao = dataPublicacao;
  }
  public Autor getAutor() {
    return autor;
  }
  public void setAutor(Autor autor) {
    this.autor = autor;
  }
    public BigDecimal getPreco() {
    return preco;
  }
  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Livro other = (Livro) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  @Override
  public String toString() {
    return "Livro [id=" + id + ", isbn=" + isbn + ", titulo=" + titulo + ", genero=" + genero + ", dataPublicacao="
        + dataPublicacao + ", preco=" + preco + ", autor=" + autor + "]";
  }
}