package loja;
import java.util.ArrayList;

public class Loja {
    private String nome;
    private String endereco;
    private double faturamento = 0.0;
    private ArrayList<Produto> produtos = new ArrayList<Produto>();

    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void addProduto(Produto produto){
        produtos.add(produto);
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    
    public String getListaProdutos(){
        String listaProdutos = "Produtos da loja :\n";

        if(produtos.isEmpty()){
            listaProdutos += "Nenhum produto cadastrado.\n";
        }

        for(Produto produto : produtos){
            listaProdutos += "- Cód: " + produto.getCodigo() + " | " + produto.getDescricao() + " | Preço: R$" + String.format("%.2f", produto.getPreco()) + " | Estoque: " + produto.getQuantidadeEstoque() + "\n";
        }

        return listaProdutos;
    }
    
    public Loja(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
    }

    public void adicionarFaturamento(double valor){
        faturamento += valor; // valor sempre vai ser positivo, pois é garantido pela classe Produto
        // qtd * produto.getPreco();
    }

    public double getFaturamento(){
        return faturamento;
    }

    public String gerarRelatorioDeVendas() {
        // loja
        String relatorio = "------------- RELATÓRIO DE VENDAS --------------\n";
        relatorio += "Loja: " + nome + "\n";
        relatorio += "Localização: " + endereco + "\n\n";
        
        // total vendido
        relatorio += "Total Vendido (R$): " + String.format("%.2f", this.faturamento) + "\n";
        relatorio += "--------------------------------------------------\n";
        
        relatorio += getListaProdutos();
        
        relatorio += "--------------------------------------------------\n";
        // os hífes estão desalinhados, o importante é a intenção
        
        return relatorio.toString();
    }

    public Produto buscarProduto(long cod) {
        for(Produto prd : produtos) {
            if(prd.getCodigo() == cod) {
                return prd;
            }
        }

        return null;
    }
}
