package io.github.SavioRomario10.LibraryApi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.github.SavioRomario10.LibraryApi.services.TransacaoService;

@SpringBootTest
public class TransacoesTest {

  @Autowired
  TransacaoService transacaoService;

  @Test
  void transacaoSimples(){
    transacaoService.execuatar();
  }
}
