package src.exceptions;

public class InvalidEntryException extends BussinesException {

    public InvalidEntryException(String campo) {
        super("Entrada inválida para o campo " + campo);
    }
}
