package src.exception;

public class SaldoInsuficienteException extends RuntimeException {
  public SaldoInsuficienteException(String mensagem) {
    super(mensagem);
  }

  // aplicando polimorfismo
  public SaldoInsuficienteException() {
    super("Saldo insuficiente para realizar a operação");
  }
}
