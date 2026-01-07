package src.utils;

import src.exceptions.BussinesException;
import src.exceptions.InvalidEntryException;
import src.exceptions.OperationCanceledException;

// muitos métodos 👍
public class Validator {

    public static void validarPreco(double preco) throws BussinesException {
        if (preco < 0) {
            throw new BussinesException("Preço não pode ser negativo");
        }
    }

    public static void validarQuantidade(int quantidade)
        throws BussinesException {
        if (quantidade <= 0) {
            throw new BussinesException(
                "Quantidade não pode ser negativa ou zero"
            );
        }
    }

    public static void validarQuantidadePositiva(int quantidade)
        throws BussinesException {
        if (quantidade <= 0) {
            throw new BussinesException("Quantidade deve ser maior que zero");
        }
    }

    public static void validarTextoVazio(String texto, String campo)
        throws BussinesException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new BussinesException(campo + " não pode ser vazio");
        }
    }

    public static void validarNaoCancelado(String entrada)
        throws OperationCanceledException {
        if (entrada == null) {
            throw new OperationCanceledException("Entrada não pode ser vazia");
        }
    }

    public static void validarNaoCancelado(Object entrada)
        throws OperationCanceledException {
        if (entrada == null) {
            throw new OperationCanceledException("Entrada não pode ser vazia");
        }
    }

    public static void validarEmail(String email) throws BussinesException {
        // regex é vida, ou sofre por não saber ou sofre aprendendo
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BussinesException("Email inválido");
        }
    }

    public static void validarCPF(String cpf) throws BussinesException {
        // vou pensar se uso ou não
        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new BussinesException("CPF inválido");
        }
    }

    public static int validarInteiro(String entrada, String campo)
        throws InvalidEntryException {
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            throw new InvalidEntryException(campo + " inválido");
        }
    }

    public static double validarDouble(String entrada, String campo)
        throws InvalidEntryException {
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            throw new InvalidEntryException(campo + " inválido");
        }
    }

    public static void validarCnpj(String cnpj) throws BussinesException {
        if (cnpj == null || cnpj.length() != 14) {
            throw new BussinesException("CNPJ deve ter exatamente 14 dígitos");
        }

        if (!cnpj.matches("\\d{14}")) {
            throw new BussinesException("CNPJ deve conter apenas números");
        }
    }

    public static void validarCep(String cep) throws BussinesException {
        if (cep == null || cep.length() != 8) {
            throw new BussinesException("CEP deve ter exatamente 8 dígitos");
        }

        if (!cep.matches("\\d{8}")) {
            throw new BussinesException("CEP deve conter apenas números");
        }
    }

    public static void validarNumero(
        String numero,
        int maxDigitos,
        String campo
    ) throws BussinesException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new BussinesException(campo + " não pode ser vazio");
        }

        if (!numero.matches("\\d+")) {
            throw new BussinesException(campo + " deve conter apenas números");
        }

        if (numero.length() > maxDigitos) {
            throw new BussinesException(
                campo + " deve ter no máximo " + maxDigitos + " dígitos"
            );
        }
    }

    public static void validarTelefone(String telefone)
        throws BussinesException {
        if (telefone == null || telefone.isEmpty()) {
            throw new BussinesException("Telefone não pode ser vazio");
        }

        String apenasNumeros = telefone.replaceAll("[^0-9]", "");

        if (apenasNumeros.length() < 10 || apenasNumeros.length() > 11) {
            throw new BussinesException(
                "Telefone deve ter 10 ou 11 dígitos (com DDD)"
            );
        }
    }
}
