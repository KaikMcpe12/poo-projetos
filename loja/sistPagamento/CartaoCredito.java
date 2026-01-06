package sistPagamento;
import javax.swing.JOptionPane;

import base.Pagamento;

public class CartaoCredito extends Pagamento{
    String numCartao;
    
    public CartaoCredito(double valor, String numCartao){
        super(valor);
        this.numCartao = numCartao;
    }     

    @Override
    public void processarPagamento() {
       JOptionPane.showMessageDialog(null, "Pagamento de R$ "+ super.valor+ ""
               + " foi realizado com o Cartão de Crédito - "+numCartao);
        
    }
}
