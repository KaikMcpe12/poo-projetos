package base;

import javax.swing.JOptionPane;

public abstract class Pagamento {
    public double valor;
    
     public Pagamento(double valor){
         this.valor=valor;
     }
     
    public abstract void processarPagamento();
     
     public void exibirValor(){
         JOptionPane.showMessageDialog(null, ""
                 + "Valor do pagamento: R$ "+valor);
     }
    
}
