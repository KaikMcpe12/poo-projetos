import java.util.ArrayList;

import javax.swing.JOptionPane;

import loja.Loja;
import loja.Produto;
import sistPagamento.SistemaPag;

public class Main {
    

    public static void main(String[] args) {
        // não liga se os hífes estão desalinhados
        String nomeLoja = JOptionPane.showInputDialog("""
                    --------------------- LOJA -----------------------
                              Entre com o nome da loja
                    --------------------------------------------------
                """);
    
        String localizacao = JOptionPane.showInputDialog("""
                    --------------------- LOJA -----------------------
                              Entre com a localizacao da loja
                    --------------------------------------------------
                """);

        Loja loja = new Loja(nomeLoja, localizacao);
        SistemaPag st = new SistemaPag();

        int menu;
        do {
            menu = Integer.parseInt(JOptionPane.showInputDialog("""
                --------------------- LOJA -----------------------
                          Escolha uma opcao do menu:

                           1 - Adicionar Produto
                           2 - Listar Produtos
                           3 - Vender Produto
                           4 - Relatorio de Vendas
                           0 - Sair

                --------------------------------------------------
                """));

            switch (menu) {
                case 1:
                    String descricao = JOptionPane.showInputDialog("Digite a descricao do produto:");
                    
                    double preco;
                    do {
                        preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preco do produto:"));
                    } while(preco < 0);

                    int qtdEstoque;
                    do {
                        qtdEstoque = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade em estoque do produto:"));
                    } while(qtdEstoque < 0);

                    Produto pr = new Produto(descricao, preco, qtdEstoque);

                    loja.addProduto(pr);
                    break;

                case 2:
                    String listaProdutos = loja.getListaProdutos();
                    JOptionPane.showMessageDialog(null, listaProdutos);

                    break;
                
                case 3:
                    if(loja.getProdutos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado!");
                        break;
                    }

                    ArrayList<Produto> produtos = loja.getProdutos();

                    Object[] opcoesProdutos = produtos.toArray();

                    Produto produtoVendido = (Produto) JOptionPane.showInputDialog(
                            null, // null
                            "Digite o codigo do produto a ser vendido:", // mensagem
                            "Seleção de produtos", // titulo
                            JOptionPane.QUESTION_MESSAGE, // tipo
                            null, // irrelevante
                            opcoesProdutos, // lista
                            opcoesProdutos[0] // seleção inicial
                    );

                    if(produtoVendido == null) {
                        JOptionPane.showMessageDialog(null, "Seleção cancelada"); // caso o usuário clique em cancelar
                        break;
                    }

                    // verifica se tem estoque
                    if(produtoVendido.getQuantidadeEstoque() == 0) {
                        JOptionPane.showMessageDialog(null, "Produto sem estoque!");
                        break;
                    }

                    // pede a quantidade a ser vendida
                    int qtdVenda;
                    do {
                        qtdVenda = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade a ser vendida:"));
                    } while(qtdVenda < 0);

                    // quantidade em estoque insuficiente
                    if(produtoVendido.getQuantidadeEstoque() < qtdVenda) {
                        JOptionPane.showMessageDialog(null, "Quantidade em estoque insuficiente!");
                        break;
                    }

                    double valorTotal = produtoVendido.getPreco() * qtdVenda;
                    boolean sucesso = st.sisPag(valorTotal);

                    if(!sucesso) {
                        JOptionPane.showMessageDialog(null, "Pagamento não autorizado!");
                        break;
                    }

                    loja.adicionarFaturamento(valorTotal);
                    produtoVendido.diminuirEstoque(qtdVenda);

                    JOptionPane.showMessageDialog(null, "Venda realizada com sucesso! Valor total: R$" + String.format("%.2f", valorTotal));
                    break;

                case 4:
                    String relatorio = loja.gerarRelatorioDeVendas();
                    JOptionPane.showMessageDialog(null, relatorio);
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Encerrando o sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Valor inválido");
                    break;
            }
        } while(menu != 0);
    }
}
