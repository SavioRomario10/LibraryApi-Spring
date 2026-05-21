package io.github.SavioRomario10.LibraryApi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.SavioRomario10.LibraryApi.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,UUID>{

  Client findByClientId(String clientId);
}