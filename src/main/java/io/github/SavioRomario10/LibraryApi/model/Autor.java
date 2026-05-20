package io.github.SavioRomario10.LibraryApi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Collections;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="autor", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Livro> livros;

  @CreatedDate
  @Column(name = "data_cadastro", nullable = false)
  private LocalDateTime dataCadastro;

  @LastModifiedDate
  @Column(name = "data_atualizacao")
  private LocalDateTime dataAtualizacao;

  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  public Autor() {}
  public Autor(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
    this.id = id;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.nacionalidade = nacionalidade;
  }

  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public LocalDate getDataNascimento() {
    return dataNascimento;
  }
  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }
  public String getNacionalidade() {
    return nacionalidade;
  }
  public void setNacionalidade(String nacionalidade) {
    this.nacionalidade = nacionalidade;
  }  
  public List<Livro> getLivros() {
    return livros != null ? livros : Collections.emptyList();
  }
  public void setLivros(List<Livro> livros) {
    this.livros = livros;
  } 

  public LocalDateTime getDataCadastro() {
    return dataCadastro;
  }
  public void setDataCadastro(LocalDateTime dataCadastro) {
    this.dataCadastro = dataCadastro;
  }
  public LocalDateTime getDataAtualizacao() {
    return dataAtualizacao;
  }
  public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }
    public Usuario getUsuario() {
    return usuario;
  }
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
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
    Autor other = (Autor) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  @Override
  public String toString() {
    return "Autor [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", nacionalidade="
        + nacionalidade + "]";
  }
}