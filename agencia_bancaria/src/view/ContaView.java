package src.view;

import src.model.ContaBancaria;

public class ContaView implements IContaView {
    private final IMessageView messageView;
    private final IInputView inputView;

    // inversão de dependência
    // boa prática 
    public ContaView(IMessageView messageView, IInputView inputView) {
        this.messageView = messageView;
        this.inputView = inputView;
    }

    @Override
    public IMessageView getMessageView() {
        return messageView;
    }

    @Override
    public IInputView getInputView() {
        return inputView;
    }

    public void exibirBoasVindas(ContaBancaria conta) {
        String mensagem = String.format("""
            === Bem-vindo(a) ao Banco UFC ===
            
            Titular: %s
            Número da Conta: %s
            Saldo: R$%.2f
            """,
            conta.getTitular().getNome(),
            conta.getNumeroConta(),
            conta.getSaldo()
        );
        messageView.exibirMensagem(mensagem);
    }

    public void exibirDadosConta(ContaBancaria conta) {
        String dados = String.format("""
            === Dados da Conta ===
            
            Titular: %s
            Email: %s
            CPF: %s
            Número da Conta: %s
            Saldo: R$%.2f
            """,
            conta.getTitular().getNome(),
            conta.getTitular().getEmail(),
            conta.getTitular().getCPF(),
            conta.getNumeroConta(),
            conta.getSaldo()
        );
        messageView.exibirMensagem(dados);
    }

    public int exibirMenuConta() {
        String menu = """
            === Menu da Conta ===
            
            1 - Saque
            2 - Depósito
            3 - Transferência
            4 - Consultar Dados
            5 - Sair
            
            Escolha uma opção:
            """;
        
        return inputView.solicitarOpcaoMenu(menu).orElse(-1);
    }

    public void exibirSucessoOperacao(String tipoOperacao, double valor, double novoSaldo) {
        messageView.exibirSucesso(String.format(
            "%s de R$%.2f realizado com sucesso!\nNovo saldo: R$%.2f",
            tipoOperacao, valor, novoSaldo
        ));
    }

    public void exibirSucessoTransferencia(String nomeOrigem, String nomeDestino, double valor, double novoSaldo) {
        messageView.exibirSucesso(String.format("""
            Transferência realizada com sucesso!
            
            De: %s
            Para: %s
            Valor: R$%.2f
            Novo saldo: R$%.2f
            """,
            nomeOrigem, nomeDestino, valor, novoSaldo
        ));
    }
}
