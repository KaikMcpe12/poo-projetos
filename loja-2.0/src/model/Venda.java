package src.model;

public class Venda {
    private final Produto produto;
    private final int quantidade;
    private final double valorTotal;
    private final String formaPagamento;
    
    public Venda(Produto produto, int quantidade, double valorTotal, String formaPagamento) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    public String toString() {
        return "Produto: " + produto.getDescricao() + ", Quantidade: " + quantidade + ", Valor Total: " + valorTotal + ", Forma de Pagamento: " + formaPagamento;
    }
}
