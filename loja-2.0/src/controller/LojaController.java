package src.controller;

import src.exceptions.BussinesException;
import src.exceptions.InvalidEntryException;
import src.exceptions.OperationCanceledException;
import src.model.Contato;
import src.model.Endereco;
import src.model.Loja;
import src.model.Produto;
import src.service.LojaService;
import src.utils.enums.Status;
import src.utils.enums.TipoLoja;
import src.view.LojaView;

import java.util.List;

public class LojaController {
    private final LojaService lojaService;
    private final LojaView view;

    public LojaController(LojaService lojaService, LojaView view) {
        this.lojaService = lojaService;
        this.view = view;
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            try {
                int opcao = view.exibirMenuInicial();

                switch (opcao) {
                    case 1 -> cadastrarLoja();
                    case 2 -> selecionarLoja();
                    case 3 -> listarLojas();
                    case 0 -> {
                        view.exibirMensagem("Encerrando o sistema...");
                        continuar = false;
                    }
                    default -> view.exibirErro("Opção inválida");
                }
            } catch (OperationCanceledException e) {
                view.exibirMensagem(e.getMessage());
            } catch (InvalidEntryException e) {
                view.exibirErro(e.getMessage());
            } catch (BussinesException e) {
                view.exibirErro(e.getMessage());
            } catch (Exception e) {
                view.exibirErro("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private void cadastrarLoja() throws InvalidEntryException, OperationCanceledException, BussinesException {
        String cnpj = view.solicitarCnpj();
        String nome = view.solicitarNomeLoja();
        TipoLoja tipoLoja = view.solicitarTipoLoja();

        String cep = view.solicitarCep();
        String estado = view.solicitarEstado();
        String cidade = view.solicitarCidade();
        String bairro = view.solicitarBairro();
        String rua = view.solicitarRua();
        String numero = view.solicitarNumero();

        Endereco endereco = lojaService.criarEndereco(cep, estado, cidade, bairro, rua, numero);

        String telefone = view.solicitarTelefone();
        String email = view.solicitarEmail();

        Contato contato = lojaService.criarContato(telefone, email);

        lojaService.cadastrarLoja(cnpj, nome, endereco, tipoLoja, contato);
        view.exibirMensagem("Loja cadastrada com sucesso!");
    }

    private void selecionarLoja() throws OperationCanceledException, BussinesException {
        if (!lojaService.existemLojas()) {
            view.exibirErro("Nenhuma loja cadastrada!");
            return;
        }

        List<Loja> lojas = lojaService.listarLojas();
        Loja loja = view.selecionarLoja(lojas);

        if (!loja.isAtiva()) {
            view.exibirErro("Esta loja está " + loja.getStatus() + ". Não é possível realizar operações.");
            return;
        }

        gerenciarLoja(loja);
    }

    private void listarLojas() {
        List<Loja> lojas = lojaService.listarLojas();
        view.exibirLojas(lojas);
    }

    private void gerenciarLoja(Loja loja) {
        boolean continuar = true;
        while (continuar) {
            try {
                int opcao = view.exibirMenuLoja(loja);

                switch (opcao) {
                    case 1 -> adicionarProduto(loja);
                    case 2 -> listarProdutos(loja);
                    case 3 -> buscarProdutos(loja);
                    case 4 -> venderProduto(loja);
                    case 5 -> exibirRelatorio(loja);
                    case 6 -> alterarStatus(loja);
                    case 0 -> continuar = false;
                    default -> view.exibirErro("Opção inválida");
                }
            } catch (OperationCanceledException e) {
                view.exibirMensagem(e.getMessage());
            } catch (InvalidEntryException e) {
                view.exibirErro(e.getMessage());
            } catch (BussinesException e) {
                view.exibirErro(e.getMessage());
            } catch (Exception e) {
                view.exibirErro("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private void adicionarProduto(Loja loja) throws InvalidEntryException, OperationCanceledException, BussinesException {
        String descricao = view.solicitarDescricaoProduto();
        double preco = view.solicitarPrecoProduto();
        int quantidade = view.solicitarQuantidadeEstoque();

        lojaService.adicionarProduto(loja.getCnpj(), descricao, preco, quantidade);
        view.exibirMensagem("Produto adicionado com sucesso!");
    }

    private void listarProdutos(Loja loja) {
        List<Produto> produtos = lojaService.listarProdutos(loja.getCnpj());
        view.exibirListaProdutos(produtos);
    }

    private void buscarProdutos(Loja loja) throws OperationCanceledException, BussinesException {
        String termo = view.solicitarTermoBusca();
        List<Produto> produtos = lojaService.buscarProdutosPorNome(termo, loja.getCnpj());

        if (produtos.isEmpty()) {
            view.exibirMensagem("Nenhum produto encontrado com esse nome.");
        } else {
            view.exibirListaProdutos(produtos);
        }
    }

    private void venderProduto(Loja loja) throws InvalidEntryException, OperationCanceledException, BussinesException {
        if (!lojaService.existemProdutos(loja.getCnpj())) {
            view.exibirErro("Nenhum produto cadastrado!");
            return;
        }

        List<Produto> produtos = lojaService.listarProdutos(loja.getCnpj());
        Produto produtoSelecionado = view.selecionarProduto(produtos);

        int quantidade = view.solicitarQuantidadeVenda();
        double valorTotal = lojaService.calcularValorVenda(produtoSelecionado, quantidade);

        int tipoPagamento = view.exibirMenuPagamento();
        if (tipoPagamento == 0) {
            throw new OperationCanceledException("Pagamento cancelado");
        }

        String dadosPagamento = obterDadosPagamento(tipoPagamento);

        lojaService.realizarVenda(loja.getCnpj(), produtoSelecionado, quantidade, tipoPagamento, dadosPagamento);
        view.exibirConfirmacaoVenda(valorTotal);

        if (loja.deveSerBloqueada()) {
            view.exibirErro("ATENÇÃO: Loja bloqueada automaticamente (caixa zerado após 3 operações)!");
        }
    }

    private String obterDadosPagamento(int tipo) throws OperationCanceledException {
        return switch (tipo) {
            case 1 -> view.solicitarIdentificacaoPix();
            case 2, 3 -> view.solicitarNumeroCartao();
            default -> throw new OperationCanceledException("Tipo de pagamento inválido");
        };
    }

    private void exibirRelatorio(Loja loja) {
        double faturamento = lojaService.obterFaturamentoTotal(loja.getCnpj());
        List<Produto> produtos = lojaService.listarProdutos(loja.getCnpj());
        view.exibirRelatorioVendas(loja, faturamento, produtos);
    }

    private void alterarStatus(Loja loja) throws OperationCanceledException, BussinesException {
        Status novoStatus = view.solicitarNovoStatus();

        String motivo = null;
        if (novoStatus == Status.INATIVA || novoStatus == Status.PENDENTE) {
            motivo = view.solicitarMotivo();
        }

        lojaService.alterarStatusLoja(loja.getCnpj(), novoStatus, motivo);
        view.exibirMensagem("Status da loja alterado com sucesso!");
    }
}
