package src.controller;

import src.exception.*;
import src.model.ContaBancaria;
import src.service.IContaService;
import src.view.IContaView;
import src.view.IInputView;
import src.view.IMessageView;

import java.util.List;
import java.util.Optional;

public class ContaController {
    private final IContaService contaService;
    private final IContaView contaView;

    public ContaController(IContaService contaService, IContaView contaView) {
        this.contaService = contaService;
        this.contaView = contaView;
    }

    public void gerenciarConta(ContaBancaria conta) {
        contaView.exibirBoasVindas(conta);
        
        boolean continuar = true;
        while (continuar) {
            try {
                int opcao = contaView.exibirMenuConta();
                
                switch (opcao) {
                    case 1 -> realizarSaque(conta);
                    case 2 -> realizarDeposito(conta);
                    case 3 -> realizarTransferencia(conta);
                    case 4 -> contaView.exibirDadosConta(conta);
                    case 5 -> {
                        contaView.getMessageView().exibirMensagem("Encerrando sessão...");
                        continuar = false;
                    }
                    default -> contaView.getMessageView().exibirErro("Opção inválida!");
                }
            } catch (SaldoInsuficienteException e) {
                contaView.getMessageView().exibirErro("Saldo Insuficiente: " + e.getMessage());
            } catch (ValorInvalidoException e) {
                contaView.getMessageView().exibirErro("Valor Inválido: " + e.getMessage());
            } catch (ContaInexistenteException e) {
                contaView.getMessageView().exibirErro("Conta Inexistente: " + e.getMessage());
            } catch (Exception e) {
                contaView.getMessageView().exibirErro("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private void realizarSaque(ContaBancaria conta) {
        IInputView inputView = contaView.getInputView();
        IMessageView messageView = contaView.getMessageView();
        
        String mensagem = String.format(
            "Saldo disponível: R$%.2f\n\nInsira o valor do saque:",
            conta.getSaldo()
        );
        
        Optional<Double> valorOpt = inputView.solicitarValorMonetarioPositivo(mensagem);
        if (valorOpt.isEmpty()) {
            messageView.exibirAviso("Operação cancelada");
            return;
        }
        
        double valor = valorOpt.get();
        contaService.realizarSaque(conta, valor);
        contaView.exibirSucessoOperacao("Saque", valor, conta.getSaldo());
    }

    private void realizarDeposito(ContaBancaria conta) {
        IInputView inputView = contaView.getInputView();
        IMessageView messageView = contaView.getMessageView();
        
        Optional<Double> valorOpt = inputView.solicitarValorMonetarioPositivo("Insira o valor do depósito:");
        if (valorOpt.isEmpty()) {
            messageView.exibirAviso("Operação cancelada");
            return;
        }
        
        double valor = valorOpt.get();
        contaService.realizarDeposito(conta, valor);
        contaView.exibirSucessoOperacao("Depósito", valor, conta.getSaldo());
    }

    private void realizarTransferencia(ContaBancaria origem) {
        IInputView inputView = contaView.getInputView();
        IMessageView messageView = contaView.getMessageView();
        
        List<ContaBancaria> contas = contaService.listarTodasContas();
        contas.remove(origem);
        
        if (contas.isEmpty()) {
            messageView.exibirAviso("Não há outras contas disponíveis para transferência");
            return;
        }
        
        ContaBancaria[] arrayContas = contas.toArray(new ContaBancaria[0]);
        Optional<ContaBancaria> destinoOpt = inputView.solicitarSelecaoLista(
            "Selecione a conta destino:", "Transferência", arrayContas
        );
        
        if (destinoOpt.isEmpty()) {
            messageView.exibirAviso("Operação cancelada");
            return;
        }
        
        ContaBancaria destino = destinoOpt.get();
        
        String mensagem = String.format(
            "Saldo disponível: R$%.2f\n\nInsira o valor da transferência:",
            origem.getSaldo()
        );
        
        Optional<Double> valorOpt = inputView.solicitarValorMonetarioPositivo(mensagem);
        if (valorOpt.isEmpty()) {
            messageView.exibirAviso("Operação cancelada");
            return;
        }
        
        double valor = valorOpt.get();
        contaService.realizarTransferencia(origem, destino, valor);
        
        contaView.exibirSucessoTransferencia(
            origem.getTitular().getNome(),
            destino.getTitular().getNome(),
            valor,
            origem.getSaldo()
        );
    }
}
