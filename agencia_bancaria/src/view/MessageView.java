package src.view;

import javax.swing.JOptionPane;

public class MessageView implements IMessageView {

    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Informação", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", 
            JOptionPane.ERROR_MESSAGE);
    }

    public void exibirSucesso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirAviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", 
            JOptionPane.WARNING_MESSAGE);
    }
}
