package src.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import src.model.Produto;

public class ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    public List<Produto> buscarPorLoja(String cnpjLoja) {
        return produtos
            .stream()
            .filter(p -> p.getCnpjLoja().equals(cnpjLoja))
            .collect(Collectors.toList());
    }

    public Optional<Produto> buscarPorCodigoELoja(
        long codigo,
        String cnpjLoja
    ) {
        return produtos
            .stream()
            .filter(
                p -> p.getCodigo() == codigo && p.getCnpjLoja().equals(cnpjLoja)
            )
            .findFirst();
    }

    public List<Produto> buscarPorNomeNaLoja(String termo, String cnpjLoja) {
        String termoBusca = termo.toLowerCase().trim();
        return produtos
            .stream()
            .filter(p -> p.getCnpjLoja().equals(cnpjLoja))
            .filter(p -> p.getDescricao().toLowerCase().contains(termoBusca))
            .collect(Collectors.toList());
    }

    public boolean existeProdutosNaLoja(String cnpjLoja) {
        return produtos
            .stream()
            .anyMatch(p -> p.getCnpjLoja().equals(cnpjLoja));
    }
}
