package loja;
import java.lang.Math;

import javax.swing.JOptionPane;

public class Produto {
    private long codigo;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    
    public long getCodigo(){
        return codigo;
    }

    public double getPreco(){
        return preco;
    }

    public boolean setPreco(double preco){
        if(preco < 0) {
            JOptionPane.showMessageDialog(null, "Preço inválido");
            return false;
        }

        this.preco = preco;
        return true;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public int getQuantidadeEstoque(){
        return quantidadeEstoque;
    }

    public boolean setQuantidadeEstoque(int quantidadeEstoque){
        if(quantidadeEstoque < 0) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida");
            return false;
        }

        this.quantidadeEstoque = quantidadeEstoque;
        return true;
    }

    public Produto(String descricao, double preco, int quantidadeEstoque){
        this.codigo = gerarCodigo();
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
    }

    public long gerarCodigo(){
        return (long)(Math.random() * 900000000) + 100000000;
    }

    public boolean diminuirEstoque(int qtd) {
        if(qtd > quantidadeEstoque) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida");
            return false;
        } 
        
        quantidadeEstoque -= qtd;
        return true;
    }
    // poderia ter o método aumentarEstoque, mas não quero complicar mais ainda, pois não vou usar

    @Override
    public String toString() {
        return codigo + " - " + descricao; 
    }
}
