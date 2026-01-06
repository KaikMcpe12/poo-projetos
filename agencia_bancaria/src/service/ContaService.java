package src.service;

import src.exception.ContaInexistenteException;
import src.exception.ValorInvalidoException;
import src.model.ContaBancaria;
import src.model.Pessoa;
import src.repository.IContaRepository;

import java.util.List;

public class ContaService implements IContaService {
    private final IContaRepository contaRepository;

    public ContaService(IContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public ContaBancaria criarConta(Pessoa titular, double saldoInicial) {
        if (contaRepository.existeEmail(titular.getEmail())) {
            throw new ValorInvalidoException("Já existe uma conta com este email");
        }
        
        if (contaRepository.existeCpf(titular.getCPF())) {
            throw new ValorInvalidoException("Já existe uma conta com este CPF");
        }
        
        ContaBancaria novaConta = new ContaBancaria(titular, saldoInicial);
        contaRepository.adicionar(novaConta);
        return novaConta;
    }

    @Override
    public void realizarSaque(ContaBancaria conta, double valor) {
        conta.sacar(valor);
    }

    @Override
    public void realizarDeposito(ContaBancaria conta, double valor) {
        conta.depositar(valor);
    }

    @Override
    public void realizarTransferencia(ContaBancaria origem, ContaBancaria destino, double valor) {
        origem.transferir(destino, valor);
    }

    @Override
    public List<ContaBancaria> listarTodasContas() {
        return contaRepository.listarTodas();
    }

    @Override
    public ContaBancaria buscarContaPorNumero(String numeroConta) {
        return contaRepository.buscarPorNumero(numeroConta)
            .orElseThrow(() -> new ContaInexistenteException(
                "Conta não encontrada: " + numeroConta
            ));
    }
}
