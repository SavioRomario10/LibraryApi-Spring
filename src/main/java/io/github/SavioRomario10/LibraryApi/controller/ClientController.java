package io.github.SavioRomario10.LibraryApi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.SavioRomario10.LibraryApi.model.Client;
import io.github.SavioRomario10.LibraryApi.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clientes")
@Slf4j
public class ClientController {

  private final ClientService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('GERENTE')")
  @Operation(summary = "Salvar", description = "Salva um novo cliente no sistema.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
    @ApiResponse(responseCode = "422", description = "Erro de validação."),
    @ApiResponse(responseCode = "409", description = "Requisição inválida.")
  })
  public void salvar(@RequestBody Client client){

    log.info("Cadastrando um novo cliente: {} e escope {}", client.getClientId(), client.getScope());

    service.salvar(client);
  }
}
