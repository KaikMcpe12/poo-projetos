package src.service;

import src.model.ContaBancaria;
import src.repository.IContaRepository;
import java.util.Optional;

public class AutenticacaoService implements IAutenticacaoService {
    private final IContaRepository contaRepository;

    public AutenticacaoService(IContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Optional<ContaBancaria> autenticar(String email, String senha) {
        return contaRepository.buscarPorEmail(email)
            .filter(conta -> conta.getTitular().getSenha().equals(senha));
    }
}
