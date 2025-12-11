package sistPagamento;

import javax.swing.*;

public class SistemaPag {
    public boolean sisPag(Double valor) {
        // novamente, os hífens estão desalinhados

        int menu;

        do {
            menu = Integer.parseInt(JOptionPane.showInputDialog("""
                -------------- SISTEMA DE PAGAMENTO --------------
                           Qual a forma de pagamento?

                            1 - PIX
                            2 - Cartão de Débito
                            3 - Cartao de Crédito
                            0 - Sair

                --------------------------------------------------
                """));

            switch (menu) {
                case 1:
                    String iden = JOptionPane.showInputDialog("Entre com a identificacao do cliente (CPF)");
                    Pix pix = new Pix(valor, iden);
                    JOptionPane.showMessageDialog(null, "Processando pagamento via PIX de R$" + String.format("%.2f", valor));
                    pix.processarPagamento();
                    return true;

                case 2:
                    String numCartao = JOptionPane.showInputDialog("Insira o numero do cartao de debito");
                    CartaoDebito cD = new CartaoDebito(valor, numCartao);
                    JOptionPane.showMessageDialog(null, "Processando pagamento via Cartão de Débito de R$" + String.format("%.2f", valor));
                    cD.processarPagamento();
                    return true;

                case 3:
                    String numCartaoC = JOptionPane.showInputDialog("Insira o numero do cartao de credito");
                    CartaoCredito cC = new CartaoCredito(valor, numCartaoC);
                    JOptionPane.showMessageDialog(null, "Processando pagamento via Cartão de Crédito de R$" + String.format("%.2f", valor));
                    cC.processarPagamento();
                    return true;

                case 0:
                    JOptionPane.showMessageDialog(null, "Encerrando operação...");
                    return false;

                default:
                    JOptionPane.showMessageDialog(null, "Valor inválido");
            }
        } while (menu != 0);
        
        return false;
    }
}
