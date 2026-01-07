package src.model;

import java.util.Random;

public class Produto {
    private final long codigo;
    private final String descricao;
    private final double preco;
    private int quantidadeEstoque;
    private String cnpjLoja;
    
    public Produto(String descricao, double preco, int quantidadeEstoque, String cnpjLoja) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.codigo = gerarCodigo();
        this.cnpjLoja = cnpjLoja;
    }
    
    public long getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public String getCnpjLoja() {
        return cnpjLoja;
    }
    
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public void diminuirEstoque(int qtd) {
        this.quantidadeEstoque -= qtd;
    }
    
    public void aumentarEstoque(int qtd) {
        this.quantidadeEstoque += qtd;
    }
    
    public boolean temEstoque() {
        return quantidadeEstoque > 0;
    }
    
    public boolean temEstoqueSuficiente(int qtd) {
        return quantidadeEstoque >= qtd;
    }
    
    private long gerarCodigo() {
        Random random = new Random();
        String codigo = "";
        for (int i = 0; i < 15; i++) {
            codigo += random.nextInt(10);
        }
        return Long.parseLong(codigo);
    }
    
    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }
}
