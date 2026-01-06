package src.exception;

public class ContaInexistenteException extends RuntimeException {
    public ContaInexistenteException(String message) {
        super(message);
    }

    // aplicando polimorfismo
    public ContaInexistenteException() {
        super("Conta inexistente");
    }
}
