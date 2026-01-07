package src.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.model.Loja;

public class LojaRepository {

    private final List<Loja> lojas = new ArrayList<>();

    public void salvar(Loja loja) {
        this.lojas.add(loja);
    }

    public List<Loja> listarTodas() {
        return new ArrayList<>(lojas);
    }

    public Optional<Loja> buscarPorCnpj(String cnpj) {
        return lojas
            .stream()
            .filter(l -> l.getCnpj().equals(cnpj))
            .findFirst();
    }

    public boolean existeComCnpj(String cnpj) {
        return lojas.stream().anyMatch(l -> l.getCnpj().equals(cnpj));
    }

    public boolean existeLojas() {
        return !lojas.isEmpty();
    }
}
