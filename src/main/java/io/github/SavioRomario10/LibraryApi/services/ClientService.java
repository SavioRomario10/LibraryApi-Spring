package io.github.SavioRomario10.LibraryApi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.SavioRomario10.LibraryApi.model.Client;
import io.github.SavioRomario10.LibraryApi.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository repository;
  private final PasswordEncoder encoder;

  public void salvar(Client client){
    if(client == null){
      throw new IllegalArgumentException("Client cannot be null");
    }
    var senhaCripto = encoder.encode(client.getClientSecret());
    client.setClientSecret(senhaCripto);
    repository.save(client);
  }

  public Client obterPorClientId(String clientId){
    return repository.findByClientId(clientId);
  }
}