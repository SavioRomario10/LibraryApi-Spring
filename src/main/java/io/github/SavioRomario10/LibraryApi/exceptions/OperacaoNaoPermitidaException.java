package io.github.SavioRomario10.LibraryApi.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{
  public OperacaoNaoPermitidaException(String mensagem) {
    super(mensagem);
  }
}
