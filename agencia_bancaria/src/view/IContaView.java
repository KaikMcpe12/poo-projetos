package src.view;

import src.model.ContaBancaria;

public interface IContaView {
    void exibirBoasVindas(ContaBancaria conta);
    void exibirDadosConta(ContaBancaria conta);
    int exibirMenuConta();
    void exibirSucessoOperacao(String tipoOperacao, double valor, double novoSaldo);
    void exibirSucessoTransferencia(String nomeOrigem, String nomeDestino, double valor, double novoSaldo);
    IMessageView getMessageView();
    IInputView getInputView();
}
