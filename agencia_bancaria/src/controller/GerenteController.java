package src.controller;

import src.exception.ValorInvalidoException;
import src.model.ContaBancaria;
import src.model.Pessoa;
import src.service.IContaService;
import src.view.IInputView;
import src.view.IMessageView;

import java.util.Optional;

public class GerenteController {
    private final IContaService contaService;
    private final IMessageView messageView;
    private final IInputView inputView;

    public GerenteController(IContaService contaService, IMessageView messageView, IInputView inputView) {
        this.contaService = contaService;
        this.messageView = messageView;
        this.inputView = inputView;
    }

    public void criarNovaConta() {
        try {
            Optional<String> nomeOpt = inputView.solicitarNome(
                "=== Criar Nova Conta ===\n\nInsira o nome do titular:"
            );
            if (nomeOpt.isEmpty()) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            Optional<String> cpfOpt = inputView.solicitarCpf(
                "=== Criar Nova Conta ===\n\nInsira o CPF (apenas números):"
            );
            if (cpfOpt.isEmpty()) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            Optional<String> emailOpt = inputView.solicitarEmail(
                "=== Criar Nova Conta ===\n\nInsira o email:"
            );
            if (emailOpt.isEmpty()) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            Optional<String> senhaOpt = inputView.solicitarSenha(
                "=== Criar Nova Conta ===\n\nInsira a senha (mínimo 4 caracteres):"
            );
            if (senhaOpt.isEmpty()) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            Optional<Double> saldoOpt = inputView.solicitarValorMonetario(
                "=== Criar Nova Conta ===\n\nInsira o saldo inicial:"
            );
            if (saldoOpt.isEmpty()) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            String nome = nomeOpt.get();
            String cpf = cpfOpt.get();
            String email = emailOpt.get();
            String senha = senhaOpt.get();
            double saldoInicial = saldoOpt.get();
            
            if (!confirmarDados(nome, cpf, email, saldoInicial)) {
                messageView.exibirAviso("Criação de conta cancelada");
                return;
            }
            
            Pessoa titular = new Pessoa(nome, senha, cpf, email);
            ContaBancaria novaConta = contaService.criarConta(titular, saldoInicial);
            
            messageView.exibirSucesso(String.format(
                "Conta criada com sucesso!\n\nNúmero da Conta: %s\nTitular: %s",
                novaConta.getNumeroConta(),
                novaConta.getTitular().getNome()
            ));
            
        } catch (ValorInvalidoException e) {
            messageView.exibirErro(e.getMessage());
        } catch (Exception e) {
            messageView.exibirErro("Erro ao criar conta: " + e.getMessage());
        }
    }

    private boolean confirmarDados(String nome, String cpf, String email, double saldoInicial) {
        String mensagem = String.format("""
            === Confirmar Dados ===
            
            Nome: %s
            CPF: %s
            Email: %s
            Senha: ****
            Saldo Inicial: R$%.2f
            
            Deseja confirmar a criação desta conta?
            """,
            nome, cpf, email, saldoInicial
        );
        return inputView.solicitarConfirmacao(mensagem);
    }
}
