package src.service;

import src.exceptions.BussinesException;
import src.model.Produto;
import src.repository.ProdutoRepository;
import src.utils.Validator;

import java.util.List;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarProduto(String cnpjLoja, String descricao, double preco, int quantidadeEstoque) 
            throws BussinesException {
        Validator.validarTextoVazio(descricao, "Descrição");
        Validator.validarPreco(preco);
        Validator.validarQuantidade(quantidadeEstoque);

        Produto produto = new Produto(descricao, preco, quantidadeEstoque, cnpjLoja);
        produtoRepository.salvar(produto);
    }

    public List<Produto> listarProdutosDaLoja(String cnpjLoja) {
        return produtoRepository.buscarPorLoja(cnpjLoja);
    }

    public List<Produto> buscarProdutosPorNomeNaLoja(String termo, String cnpjLoja) throws BussinesException {
        Validator.validarTextoVazio(termo, "Termo de busca");
        return produtoRepository.buscarPorNomeNaLoja(termo, cnpjLoja);
    }

    public boolean existemProdutosNaLoja(String cnpjLoja) {
        return produtoRepository.existeProdutosNaLoja(cnpjLoja);
    }

    public Produto buscarProduto(long codigo, String cnpjLoja) throws BussinesException {
        return produtoRepository.buscarPorCodigoELoja(codigo, cnpjLoja)
                .orElseThrow(() -> new BussinesException("Produto não encontrado nesta loja"));
    }
}
