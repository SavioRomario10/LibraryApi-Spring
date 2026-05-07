package io.github.SavioRomario10.LibraryApi.exception;

public class OperacaoNaoPermitidaException extends RuntimeException{
  public OperacaoNaoPermitidaException(String mensagem) {
    super(mensagem);
  }
}
