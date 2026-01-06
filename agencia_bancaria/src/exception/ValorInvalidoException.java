package src.exception;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }
    
    // apicando polimorfismo
    public ValorInvalidoException() {
        super("Valor invalido fornecido");
    }
}
