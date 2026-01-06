package src.view;

public interface IMessageView {
    void exibirMensagem(String mensagem);
    void exibirErro(String mensagem);
    void exibirSucesso(String mensagem);
    void exibirAviso(String mensagem);
}
