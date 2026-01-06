package src.repository;

import src.model.ContaBancaria;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContaRepository implements IContaRepository {
    private final List<ContaBancaria> contas;

    public ContaRepository() {
        this.contas = new ArrayList<>();
    }

    @Override
    public void adicionar(ContaBancaria conta) {
        contas.add(conta);
    }

    @Override
    public Optional<ContaBancaria> buscarPorNumero(String numeroConta) {
        return contas.stream()
            .filter(c -> c.getNumeroConta().equals(numeroConta))
            .findFirst();
    }

    @Override
    public Optional<ContaBancaria> buscarPorEmail(String email) {
        return contas.stream()
            .filter(c -> c.getTitular().getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    @Override
    public List<ContaBancaria> listarTodas() {
        return new ArrayList<>(contas);
    }

    @Override
    public boolean existeEmail(String email) {
        return contas.stream()
            .anyMatch(c -> c.getTitular().getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean existeCpf(String cpf) {
        return contas.stream()
            .anyMatch(c -> c.getTitular().getCPF().equals(cpf));
    }
}
