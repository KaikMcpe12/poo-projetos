package src.service;

import src.model.ContaBancaria;
import src.model.Pessoa;
import java.util.List;

public interface IContaService {
    ContaBancaria criarConta(Pessoa titular, double saldoInicial);
    void realizarSaque(ContaBancaria conta, double valor);
    void realizarDeposito(ContaBancaria conta, double valor);
    void realizarTransferencia(ContaBancaria origem, ContaBancaria destino, double valor);
    List<ContaBancaria> listarTodasContas();
    ContaBancaria buscarContaPorNumero(String numeroConta);
}
