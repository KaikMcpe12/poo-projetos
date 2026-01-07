package src.service;

import java.util.List;
import src.exceptions.BussinesException;
import src.model.Contato;
import src.model.Endereco;
import src.model.Loja;
import src.model.Produto;
import src.repository.LojaRepository;
import src.utils.Validator;
import src.utils.enums.Status;
import src.utils.enums.TipoLoja;

public class LojaService {

    private final ProdutoService produtoService;
    private final VendaService vendaService;
    private final PagamentoService pagamentoService;
    private final LojaRepository lojaRepository;

    public LojaService(
        LojaRepository lojaRepository,
        ProdutoService produtoService,
        VendaService vendaService,
        PagamentoService pagamentoService
    ) {
        this.lojaRepository = lojaRepository;
        this.produtoService = produtoService;
        this.vendaService = vendaService;
        this.pagamentoService = pagamentoService;
    }

    // crud loja
    public void cadastrarLoja(
        String cnpj,
        String nome,
        Endereco endereco,
        TipoLoja tipoLoja,
        Contato contato
    ) throws BussinesException {
        Validator.validarCnpj(cnpj);
        Validator.validarTextoVazio(nome, "Nome da loja");

        if (lojaRepository.existeComCnpj(cnpj)) {
            throw new BussinesException(
                "Já existe uma loja cadastrada com este CNPJ"
            );
        }

        Loja loja = new Loja(nome, endereco, tipoLoja, contato, cnpj);
        lojaRepository.salvar(loja);
    }

    public List<Loja> listarLojas() {
        return lojaRepository.listarTodas();
    }

    public Loja buscarLoja(String cnpj) throws BussinesException {
        return lojaRepository
            .buscarPorCnpj(cnpj)
            .orElseThrow(() -> new BussinesException("Loja não encontrada"));
    }

    public boolean existemLojas() {
        return lojaRepository.existeLojas();
    }

    public void alterarStatusLoja(String cnpj, Status novoStatus, String motivo)
        throws BussinesException {
        Loja loja = buscarLoja(cnpj);

        if (novoStatus == Status.INATIVA || novoStatus == Status.PENDENTE) {
            Validator.validarTextoVazio(motivo, "Motivo");
        }

        loja.setStatus(novoStatus, motivo);
    }

    // objetos
    public Endereco criarEndereco(
        String cep,
        String estado,
        String cidade,
        String bairro,
        String rua,
        String numero
    ) throws BussinesException {
        Validator.validarCep(cep);
        Validator.validarTextoVazio(estado, "Estado");
        Validator.validarTextoVazio(cidade, "Cidade");
        Validator.validarTextoVazio(bairro, "Bairro");
        Validator.validarTextoVazio(rua, "Rua");
        Validator.validarNumero(numero, 5, "Número");

        return new Endereco(cep, estado, cidade, bairro, rua, numero);
    }

    public Contato criarContato(String telefone, String email)
        throws BussinesException {
        Validator.validarTelefone(telefone);
        Validator.validarEmail(email);

        return new Contato(telefone, email);
    }

    // produtos
    public void adicionarProduto(
        String cnpjLoja,
        String descricao,
        double preco,
        int quantidadeEstoque
    ) throws BussinesException {
        produtoService.cadastrarProduto(
            cnpjLoja,
            descricao,
            preco,
            quantidadeEstoque
        );
    }

    public List<Produto> listarProdutos(String cnpjLoja) {
        return produtoService.listarProdutosDaLoja(cnpjLoja);
    }

    public List<Produto> buscarProdutosPorNome(String termo, String cnpjLoja)
        throws BussinesException {
        return produtoService.buscarProdutosPorNomeNaLoja(termo, cnpjLoja);
    }

    public boolean existemProdutos(String cnpjLoja) {
        return produtoService.existemProdutosNaLoja(cnpjLoja);
    }

    // vendas
    public double calcularValorVenda(Produto produto, int quantidade) {
        return vendaService.calcularValorVenda(produto, quantidade);
    }

    public void realizarVenda(
        String cnpjLoja,
        Produto produto,
        int quantidade,
        int tipoPagamento,
        String dadosPagamento
    ) throws BussinesException {
        String formaPagamento = processarPagamento(
            tipoPagamento,
            dadosPagamento
        );
        vendaService.realizarVenda(produto, quantidade, formaPagamento);

        Loja loja = buscarLoja(cnpjLoja);
        loja.adicionarAoCaixa(calcularValorVenda(produto, quantidade));
        verificarBloqueioAutomatico(loja);
    }

    public double obterFaturamentoTotal(String cnpjLoja) {
        return vendaService.obterFaturamentoPorLoja(cnpjLoja);
    }

    // outros
    private String processarPagamento(int tipo, String dados)
        throws BussinesException {
        return switch (tipo) {
            case 1 -> pagamentoService.processarPagamentoPix(dados);
            case 2 -> pagamentoService.processarPagamentoDebito(dados);
            case 3 -> pagamentoService.processarPagamentoCredito(dados);
            default -> throw new BussinesException(
                "Forma de pagamento inválida"
            );
        };
    }

    private void verificarBloqueioAutomatico(Loja loja) {
        if (loja.deveSerBloqueada() && loja.getStatus() != Status.BLOQUEADA) {
            loja.setStatus(Status.BLOQUEADA, "Caixa zerado após 3 operações");
        }
    }
}
// sim, uma classe com muitos métodos
