package src.exceptions;

public class OperationCanceledException extends BussinesException {
    public OperationCanceledException() {
        super("Operação cancelada");
    }
    
    public OperationCanceledException(String message) {
        super(message);
    }
}
