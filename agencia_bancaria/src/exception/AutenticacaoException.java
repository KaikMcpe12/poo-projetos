package src.exception;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String message) {
        super(message);
    }

    // aplicando polimorfismo
    public AutenticacaoException() {
        super("Erro de autenticação");
    }
}
