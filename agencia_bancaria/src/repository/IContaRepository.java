package src.repository;

import src.model.ContaBancaria;
import java.util.List;
import java.util.Optional;

public interface IContaRepository {
    void adicionar(ContaBancaria conta);
    Optional<ContaBancaria> buscarPorNumero(String numeroConta);
    Optional<ContaBancaria> buscarPorEmail(String email);
    List<ContaBancaria> listarTodas();
    boolean existeEmail(String email);
    boolean existeCpf(String cpf);
}
