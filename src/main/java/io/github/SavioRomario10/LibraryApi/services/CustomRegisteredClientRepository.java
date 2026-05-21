package io.github.SavioRomario10.LibraryApi.services;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository{

  private final ClientService service;
  private final TokenSettings token;
  private final ClientSettings settings;


  @Override
  @Nullable
  public RegisteredClient findByClientId(String clientId) {
    var client = service.obterPorClientId(clientId);
    
    if(client == null){
      return null;
    }

    return RegisteredClient
        .withId(client.getId().toString())
        .clientId(client.getClientId())
        .clientSecret(client.getClientSecret())
        .redirectUri(client.getRedirectUri())
        .scope(client.getScope())
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .tokenSettings(token)
        .clientSettings(settings) 
        .build();
  }

  @Override
  @Nullable
  public RegisteredClient findById(String id) {
    return null;
  }

  @Override
  public void save(RegisteredClient registeredClient) {

  }
}
