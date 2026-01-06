package src.view;

import java.util.Optional;

public interface IInputView {
    Optional<String> solicitarTexto(String mensagem);
    Optional<Integer> solicitarInteiro(String mensagem);
    Optional<Double> solicitarDecimal(String mensagem);
    Optional<Double> solicitarValorMonetario(String mensagem);
    Optional<Double> solicitarValorMonetarioPositivo(String mensagem);
    Optional<String> solicitarEmail(String mensagem);
    Optional<String> solicitarCpf(String mensagem);
    Optional<String> solicitarSenha(String mensagem);
    Optional<String> solicitarNome(String mensagem);
    boolean solicitarConfirmacao(String mensagem);
    Optional<Integer> solicitarOpcaoMenu(String mensagem);
    <T> Optional<T> solicitarSelecaoLista(String mensagem, String titulo, T[] opcoes);
}
