package sistPagamento;
import javax.swing.JOptionPane;

import base.Pagamento;

public class Pix extends Pagamento{
    String id;
    
    public Pix(double valor, String id) {
        super(valor);
        this.id=id;
    } 
    @Override
    public void processarPagamento() {
        JOptionPane.showMessageDialog(null, "Pagamento de"
                + "R$ "+valor+" foi realizado com a identificação"
                        + "do CPF: "+id);
        
    }
}
