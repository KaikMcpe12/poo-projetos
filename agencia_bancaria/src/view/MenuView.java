package src.view;

public class MenuView implements IMenuView {
    private final IMessageView messageView;
    private final IInputView inputView;

    public MenuView(IMessageView messageView, IInputView inputView) {
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

    public int exibirMenuPrincipal() {
        String menu = """
            === Bem-vindo ao Banco UFC ===
            
            1 - Acesso Gerente (Criar Contas)
            2 - Acesso Cliente (Entrar na Conta)
            3 - Sair
            
            Escolha uma opção:
            """;
        
        return inputView.solicitarOpcaoMenu(menu).orElse(-1);
    }
}
