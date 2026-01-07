package src.service;

import src.exceptions.BussinesException;
import src.model.Produto;
import src.model.Venda;
import src.repository.VendaRepository;
import src.utils.Validator;

public class VendaService {
    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public double calcularValorVenda(Produto produto, int quantidade) {
        return produto.getPreco() * quantidade;
    }

    public void realizarVenda(Produto produto, int quantidade, String formaPagamento) throws BussinesException {
        Validator.validarQuantidadePositiva(quantidade);

        if (!produto.temEstoque()) {
            throw new BussinesException("Produto sem estoque");
        }

        if (!produto.temEstoqueSuficiente(quantidade)) {
            throw new BussinesException("Quantidade em estoque insuficiente");
        }

        double valorTotal = calcularValorVenda(produto, quantidade);
        Venda venda = new Venda(produto, quantidade, valorTotal, formaPagamento);

        produto.diminuirEstoque(quantidade);
        vendaRepository.salvar(venda);
    }

    public double obterFaturamentoTotal() {
        return vendaRepository.calcularFaturamentoTotal();
    }

    public double obterFaturamentoPorLoja(String cnpjLoja) {
        return vendaRepository.calcularFaturamentoPorLoja(cnpjLoja);
    }
}
