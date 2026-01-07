package src.view;

import src.exceptions.InvalidEntryException;
import src.exceptions.OperationCanceledException;
import src.model.Loja;
import src.model.Produto;
import src.utils.Validator;
import src.utils.enums.Status;
import src.utils.enums.TipoLoja;

import javax.swing.JOptionPane;
import java.util.List;

public class LojaView {
    // esclarecimento: não fui eu que fiz os menus, peguei um mocelo pronto
    public int exibirMenuInicial() throws InvalidEntryException, OperationCanceledException {
        String opcao = JOptionPane.showInputDialog("""
                =============== SISTEMA DE LOJAS ================
                          Escolha uma opção:

                           1 - Cadastrar Nova Loja
                           2 - Selecionar Loja Existente
                           3 - Listar Todas as Lojas
                           0 - Sair

                ==================================================
                """);
        Validator.validarNaoCancelado(opcao);
        return Validator.validarInteiro(opcao, "Opção do menu");
    }

    public String solicitarCnpj() throws OperationCanceledException {
        String cnpj = JOptionPane.showInputDialog("Digite o CNPJ da loja (14 dígitos):");
        Validator.validarNaoCancelado(cnpj);
        return cnpj;
    }

    public String solicitarNomeLoja() throws OperationCanceledException {
        String nome = JOptionPane.showInputDialog("Digite o nome da loja:");
        Validator.validarNaoCancelado(nome);
        return nome;
    }

    public TipoLoja solicitarTipoLoja() throws OperationCanceledException {
        TipoLoja[] tipos = TipoLoja.values();
        TipoLoja tipo = (TipoLoja) JOptionPane.showInputDialog(
                null,
                "Selecione a categoria da loja:",
                "Categoria",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );
        Validator.validarNaoCancelado(tipo);
        return tipo;
    }

    public String solicitarCep() throws OperationCanceledException {
        String cep = JOptionPane.showInputDialog("Digite o CEP (8 dígitos):");
        Validator.validarNaoCancelado(cep);
        return cep;
    }

    public String solicitarEstado() throws OperationCanceledException {
        String estado = JOptionPane.showInputDialog("Digite o Estado (sigla):");
        Validator.validarNaoCancelado(estado);
        return estado;
    }

    public String solicitarCidade() throws OperationCanceledException {
        String cidade = JOptionPane.showInputDialog("Digite a Cidade:");
        Validator.validarNaoCancelado(cidade);
        return cidade;
    }

    public String solicitarBairro() throws OperationCanceledException {
        String bairro = JOptionPane.showInputDialog("Digite o Bairro:");
        Validator.validarNaoCancelado(bairro);
        return bairro;
    }

    public String solicitarRua() throws OperationCanceledException {
        String rua = JOptionPane.showInputDialog("Digite a Rua:");
        Validator.validarNaoCancelado(rua);
        return rua;
    }

    public String solicitarNumero() throws OperationCanceledException {
        String numero = JOptionPane.showInputDialog("Digite o Número (até 5 dígitos):");
        Validator.validarNaoCancelado(numero);
        return numero;
    }

    public String solicitarTelefone() throws OperationCanceledException {
        String telefone = JOptionPane.showInputDialog("Digite o Telefone (com DDD):");
        Validator.validarNaoCancelado(telefone);
        return telefone;
    }

    public String solicitarEmail() throws OperationCanceledException {
        String email = JOptionPane.showInputDialog("Digite o Email:");
        Validator.validarNaoCancelado(email);
        return email;
    }

    public Loja selecionarLoja(List<Loja> lojas) throws OperationCanceledException {
        Object[] opcoes = lojas.toArray();
        Loja loja = (Loja) JOptionPane.showInputDialog(
                null,
                "Selecione a loja:",
                "Seleção de Loja",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
        Validator.validarNaoCancelado(loja);
        return loja;
    }

    public void exibirLojas(List<Loja> lojas) {
        if (lojas.isEmpty()) {
            exibirMensagem("Nenhuma loja cadastrada.");
            return;
        }

        StringBuilder lista = new StringBuilder("========== LOJAS CADASTRADAS ==========\n\n");
        for (Loja loja : lojas) {
            lista.append(String.format("CNPJ: %s%n", loja.getCnpj()));
            lista.append(String.format("Nome: %s%n", loja.getNome()));
            lista.append(String.format("Categoria: %s%n", loja.getTipoLoja()));
            lista.append(String.format("Status: %s%n", loja.getStatus()));
            if (loja.getMotivoStatus() != null) {
                lista.append(String.format("Motivo: %s%n", loja.getMotivoStatus()));
            }
            // lista.append(String.format("Caixa: R$ %.2f%n", loja.getCaixa())); // ainda não implementado
            lista.append(String.format("Operações: %d%n", loja.getOperacoesRealizadas()));
            lista.append("----------------------------------------\n");
        }

        exibirMensagem(lista.toString());
    }

    public int exibirMenuLoja(Loja loja) throws InvalidEntryException, OperationCanceledException {
        String opcao = JOptionPane.showInputDialog(String.format("""
                ================= LOJA: %s =================
                Status: %s
                
                          Escolha uma opção:

                           1 - Adicionar Produto
                           2 - Listar Produtos
                           3 - Buscar Produtos
                           4 - Vender Produto
                           5 - Relatório de Vendas
                           6 - Alterar Status da Loja
                           0 - Voltar

                ==================================================
                """, loja.getNome(), loja.getStatus()));
                // """, loja.getNome(), loja.getStatus(), loja.getCaixa()));
        Validator.validarNaoCancelado(opcao);
        return Validator.validarInteiro(opcao, "Opção do menu");
    }

    public String solicitarDescricaoProduto() throws OperationCanceledException {
        String descricao = JOptionPane.showInputDialog("Digite a descrição do produto:");
        Validator.validarNaoCancelado(descricao);
        return descricao;
    }

    public double solicitarPrecoProduto() throws InvalidEntryException, OperationCanceledException {
        String preco = JOptionPane.showInputDialog("Digite o preço do produto:");
        Validator.validarNaoCancelado(preco);
        return Validator.validarDouble(preco, "Preço");
    }

    public int solicitarQuantidadeEstoque() throws InvalidEntryException, OperationCanceledException {
        String qtd = JOptionPane.showInputDialog("Digite a quantidade em estoque:");
        Validator.validarNaoCancelado(qtd);
        return Validator.validarInteiro(qtd, "Quantidade");
    }

    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void exibirListaProdutos(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            exibirMensagem("Nenhum produto cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder("Produtos da loja:\n\n");
        for (Produto produto : produtos) {
            lista.append(String.format("Cód: %d | %s | Preço: R$ %.2f | Estoque: %d%n",
                    produto.getCodigo(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getQuantidadeEstoque()));
        }

        exibirMensagem(lista.toString());
    }

    public String solicitarTermoBusca() throws OperationCanceledException {
        String termo = JOptionPane.showInputDialog("Digite o nome do produto para buscar:");
        Validator.validarNaoCancelado(termo);
        return termo;
    }

    public Produto selecionarProduto(List<Produto> produtos) throws OperationCanceledException {
        Object[] opcoes = produtos.toArray();
        Produto produto = (Produto) JOptionPane.showInputDialog(
                null,
                "Selecione o produto:",
                "Venda de Produto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
        Validator.validarNaoCancelado(produto);
        return produto;
    }

    public int solicitarQuantidadeVenda() throws InvalidEntryException, OperationCanceledException {
        String qtd = JOptionPane.showInputDialog("Digite a quantidade a ser vendida:");
        Validator.validarNaoCancelado(qtd);
        return Validator.validarInteiro(qtd, "Quantidade");
    }

    public int exibirMenuPagamento() throws InvalidEntryException, OperationCanceledException {
        String opcao = JOptionPane.showInputDialog("""
                -------------- SISTEMA DE PAGAMENTO --------------
                           Qual a forma de pagamento?

                            1 - PIX
                            2 - Cartão de Débito
                            3 - Cartão de Crédito
                            0 - Cancelar

                --------------------------------------------------
                """);
        Validator.validarNaoCancelado(opcao);
        return Validator.validarInteiro(opcao, "Forma de pagamento");
    }

    public String solicitarIdentificacaoPix() throws OperationCanceledException {
        String id = JOptionPane.showInputDialog("Entre com a identificação do cliente (CPF):");
        Validator.validarNaoCancelado(id);
        return id;
    }

    public String solicitarNumeroCartao() throws OperationCanceledException {
        String cartao = JOptionPane.showInputDialog("Insira o número do cartão:");
        Validator.validarNaoCancelado(cartao);
        return cartao;
    }

    public void exibirConfirmacaoVenda(double valor) {
        exibirMensagem(String.format("Venda realizada com sucesso! Valor total: R$ %.2f", valor));
    }

    public Status solicitarNovoStatus() throws OperationCanceledException {
        Status[] statusList = Status.values();
        Status novoStatus = (Status) JOptionPane.showInputDialog(
                null,
                "Selecione o novo status:",
                "Alterar Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statusList,
                statusList[0]
        );
        Validator.validarNaoCancelado(novoStatus);
        return novoStatus;
    }

    public String solicitarMotivo() throws OperationCanceledException {
        String motivo = JOptionPane.showInputDialog("Digite o motivo da alteração:");
        Validator.validarNaoCancelado(motivo);
        return motivo;
    }

    public void exibirRelatorioVendas(Loja loja, double faturamento, List<Produto> produtos) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("============= RELATÓRIO DE VENDAS =============\n");
        relatorio.append(String.format("Loja: %s (CNPJ: %s)%n", loja.getNome(), loja.getCnpj()));
        relatorio.append(String.format("Endereço: %s%n", loja.getEndereco()));
        relatorio.append(String.format("Categoria: %s%n", loja.getTipoLoja()));
        relatorio.append(String.format("Contato: %s%n%n", loja.getContato()));
        
        relatorio.append(String.format("Status: %s%n", loja.getStatus()));
        if (loja.getMotivoStatus() != null) {
            relatorio.append(String.format("Motivo: %s%n", loja.getMotivoStatus()));
        }
        // relatorio.append(String.format("Caixa Atual: R$ %.2f%n", loja.getCaixa())); // ainda não implementado
        relatorio.append(String.format("Operações Realizadas: %d%n%n", loja.getOperacoesRealizadas()));
        
        relatorio.append(String.format("Total Vendido (R$): %.2f%n", faturamento));
        relatorio.append("==================================================\n\n");

        relatorio.append("Produtos cadastrados:\n");
        for (Produto produto : produtos) {
            relatorio.append(String.format("- Cód: %d | %s | Preço: R$ %.2f | Estoque: %d%n",
                    produto.getCodigo(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getQuantidadeEstoque()));
        }

        relatorio.append("==================================================\n");
        exibirMensagem(relatorio.toString());
    }
}
