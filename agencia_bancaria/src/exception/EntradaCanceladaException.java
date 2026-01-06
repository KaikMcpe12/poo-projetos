package src.exception;

public class EntradaCanceladaException extends RuntimeException {
    public EntradaCanceladaException(String message) {
        super(message);
    }
    
    // aplicando polimorfismo
    public EntradaCanceladaException() {
        super("Operação cancelada pelo usuário");
    }
}
