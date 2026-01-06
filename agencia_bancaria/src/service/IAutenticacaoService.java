package src.service;

import src.model.ContaBancaria;
import java.util.Optional;

public interface IAutenticacaoService {
    Optional<ContaBancaria> autenticar(String email, String senha);
}
