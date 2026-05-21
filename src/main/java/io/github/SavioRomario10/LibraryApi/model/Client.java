package io.github.SavioRomario10.LibraryApi.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Client {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "client_id", length = 150, nullable = false)
  private String clientId;
  
  @Column(name = "client_secret", length = 150, nullable = false)
  private String clientSecret;
  
  @Column(name = "redirect_uri", length = 200, nullable = false)
  private String redirectUri;
  
  @Column(name = "scope", length = 200)
  private String scope;
}
