package src.service;

import src.exceptions.BussinesException;
import src.utils.Validator;

public class PagamentoService {

    public String processarPagamentoPix(String identificacao) throws BussinesException {
        Validator.validarTextoVazio(identificacao, "Identificação (CPF)");
        return "PIX";
    }

    public String processarPagamentoDebito(String numeroCartao) throws BussinesException {
        Validator.validarTextoVazio(numeroCartao, "Número do cartão");
        return "Cartão de Débito";
    }

    public String processarPagamentoCredito(String numeroCartao) throws BussinesException {
        Validator.validarTextoVazio(numeroCartao, "Número do cartão");
        return "Cartão de Crédito";
    }
}
