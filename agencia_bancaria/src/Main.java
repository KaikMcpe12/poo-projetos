package src;

import src.controller.ContaController;
import src.controller.GerenteController;
import src.exception.AutenticacaoException;
import src.model.ContaBancaria;
import src.repository.ContaRepository;
import src.repository.IContaRepository;
import src.service.*;
import src.view.*;

import java.util.Optional;

public class Main {
    // views
    private final IInputView inputView;
    private final IMessageView messageView;
    private final IContaView contaView;
    private final IMenuView menuView;
    
    // services
    private final IContaService contaService;
    private final IAutenticacaoService autenticacaoService;
    
    // controllers
    private final ContaController contaController;
    private final GerenteController gerenteController;

    public Main() {
        // view básicas
        this.inputView = new InputView();
        this.messageView = new MessageView();
        
        // view que dependem das views básicas
        this.contaView = new ContaView(messageView, inputView);
        this.menuView = new MenuView(messageView, inputView);
        
        // repository e services
        IContaRepository contaRepository = new ContaRepository();
        this.contaService = new ContaService(contaRepository);
        this.autenticacaoService = new AutenticacaoService(contaRepository);
        
        // controllers
        this.contaController = new ContaController(contaService, contaView);
        this.gerenteController = new GerenteController(contaService, messageView, inputView);
    }

    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            int opcao = menuView.exibirMenuPrincipal();
            
            switch (opcao) {
                case 1 -> gerenteController.criarNovaConta();
                case 2 -> acessoCliente();
                case 3 -> {
                    messageView.exibirMensagem("Obrigado por usar o Banco UFC!\nAté logo!");
                    continuar = false;
                }
                default -> messageView.exibirErro("Opção inválida!");
            }
        }
    }

    private void acessoCliente() {
        try {
            Optional<String> emailOpt = inputView.solicitarEmail(
                "=== Login ===\n\nInsira seu email:"
            );
            if (emailOpt.isEmpty()) {
                messageView.exibirAviso("Login cancelado");
                return;
            }
            
            Optional<String> senhaOpt = inputView.solicitarTexto(
                "=== Login ===\n\nInsira sua senha:"
            );
            if (senhaOpt.isEmpty()) {
                messageView.exibirAviso("Login cancelado");
                return;
            }
            
            String email = emailOpt.get();
            String senha = senhaOpt.get();
            
            Optional<ContaBancaria> contaOpt = autenticacaoService.autenticar(email, senha);
            
            if (contaOpt.isEmpty()) {
                throw new AutenticacaoException("Email ou senha incorretos!");
            }
            
            contaController.gerenciarConta(contaOpt.get());
            
        } catch (AutenticacaoException e) {
            messageView.exibirErro(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Main().iniciar();
    }
}
