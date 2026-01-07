package src.repository;

import src.model.Venda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendaRepository {
    private final List<Venda> vendas = new ArrayList<>();

    public void salvar(Venda venda) {
        vendas.add(venda);
    }

    public List<Venda> listarTodas() {
        return new ArrayList<>(vendas);
    }

    public List<Venda> listarPorLoja(String cnpjLoja) {
        return vendas.stream()
                .filter(v -> v.getProduto().getCnpjLoja().equals(cnpjLoja))
                .collect(Collectors.toList());
    }

    public double calcularFaturamentoTotal() {
        return vendas.stream()
                .mapToDouble(Venda::getValorTotal)
                .sum();
    }

    public double calcularFaturamentoPorLoja(String cnpjLoja) {
        return vendas.stream()
                .filter(v -> v.getProduto().getCnpjLoja().equals(cnpjLoja))
                .mapToDouble(Venda::getValorTotal)
                .sum();
    }
}
