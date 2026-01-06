package sistPagamento;
 
import base.Pagamento;
import javax.swing.JOptionPane;

public class CartaoDebito extends Pagamento{
    String numCartao;
    
    public CartaoDebito(double valor, String numCartao){
    super(valor);
    this.numCartao = numCartao;
    }     
    @Override
    public void processarPagamento() {
       JOptionPane.showMessageDialog(null, "Pagamento de R$ "+valor+""
               + " foi realizado com o Cartão de Débito - "+numCartao);
        
    }
}
