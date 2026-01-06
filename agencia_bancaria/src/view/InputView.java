package src.view;

import src.util.ValidadorUtil;

import javax.swing.JOptionPane;
import java.util.Optional;

public class InputView implements IInputView {
    // Optional é usado para representar valores que podem estar presentes ou ausentes
    public Optional<String> solicitarTexto(String mensagem) {
        String entrada = JOptionPane.showInputDialog(mensagem);
        
        if (entrada == null || entrada.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(entrada.trim());
    }

    public Optional<Integer> solicitarInteiro(String mensagem) {
        try {
            String entrada = JOptionPane.showInputDialog(mensagem);
            
            if (entrada == null || entrada.trim().isEmpty()) {
                return Optional.empty();
            }
            
            int valor = Integer.parseInt(entrada.trim());
            return Optional.of(valor);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro: Digite um número inteiro válido", 
                "Entrada Inválida", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
    }

    public Optional<Double> solicitarDecimal(String mensagem) {
        try {
            String entrada = JOptionPane.showInputDialog(mensagem);
            
            if (entrada == null || entrada.trim().isEmpty()) {
                return Optional.empty();
            }
            
            double valor = Double.parseDouble(entrada.trim().replace(",", "."));
            return Optional.of(valor);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro: Digite um número decimal válido (use . ou ,)", 
                "Entrada Inválida", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
    }

    public Optional<Double> solicitarValorMonetario(String mensagem) {
        Optional<Double> valorOpt = solicitarDecimal(mensagem);
        
        if (valorOpt.isEmpty()) {
            return Optional.empty();
        }
        
        double valor = valorOpt.get();
        
        if (valor < 0) {
            JOptionPane.showMessageDialog(null, 
                "Erro: O valor não pode ser negativo", 
                "Valor Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(valor);
    }

    public Optional<Double> solicitarValorMonetarioPositivo(String mensagem) {
        Optional<Double> valorOpt = solicitarValorMonetario(mensagem);
        
        if (valorOpt.isEmpty()) {
            return Optional.empty();
        }
        
        double valor = valorOpt.get();
        
        if (valor <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Erro: O valor deve ser maior que zero", 
                "Valor Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(valor);
    }

    public Optional<String> solicitarEmail(String mensagem) {
        Optional<String> emailOpt = solicitarTexto(mensagem);
        
        if (emailOpt.isEmpty()) {
            return Optional.empty();
        }
        
        String email = emailOpt.get();
        
        if (!ValidadorUtil.validarEmail(email)) {
            JOptionPane.showMessageDialog(null, 
                "Erro: Email inválido. Use o formato: exemplo@email.com", 
                "Email Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(email);
    }

    public Optional<String> solicitarCpf(String mensagem) {
        Optional<String> cpfOpt = solicitarTexto(mensagem);
        
        if (cpfOpt.isEmpty()) {
            return Optional.empty();
        }
        
        // remove caracteres não numéricos
        String cpf = cpfOpt.get().replaceAll("[^0-9]", "");
        
        if (!ValidadorUtil.validarCpf(cpf)) {
            JOptionPane.showMessageDialog(null, 
                "Erro: CPF inválido. Deve conter 11 dígitos", 
                "CPF Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(cpf);
    }

    public Optional<String> solicitarSenha(String mensagem) {
        Optional<String> senhaOpt = solicitarTexto(mensagem);
        
        if (senhaOpt.isEmpty()) {
            return Optional.empty();
        }
        
        String senha = senhaOpt.get();
        
        if (!ValidadorUtil.validarSenha(senha)) {
            JOptionPane.showMessageDialog(null, 
                "Erro: Senha inválida. Deve ter no mínimo 4 caracteres", 
                "Senha Inválida", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(senha);
    }

    public Optional<String> solicitarNome(String mensagem) {
        Optional<String> nomeOpt = solicitarTexto(mensagem);
        
        if (nomeOpt.isEmpty()) {
            return Optional.empty();
        }
        
        String nome = nomeOpt.get();
        
        if (!ValidadorUtil.validarNome(nome)) {
            JOptionPane.showMessageDialog(null, 
                "Erro: Nome inválido. Não pode estar vazio", 
                "Nome Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
        
        return Optional.of(nome);
    }

    public boolean solicitarConfirmacao(String mensagem) {
        int resposta = JOptionPane.showConfirmDialog(null, 
            mensagem, 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        return resposta == JOptionPane.YES_OPTION;
    }

    public Optional<Integer> solicitarOpcaoMenu(String mensagem) {
        return solicitarInteiro(mensagem);
    }

    // <T> indica que o método é genérico e pode trabalhar com qualquer tipo T
    public <T> Optional<T> solicitarSelecaoLista(String mensagem, String titulo, T[] opcoes) {
        if (opcoes == null || opcoes.length == 0) {
            return Optional.empty();
        }
        
        @SuppressWarnings("unchecked")
        T selecionado = (T) JOptionPane.showInputDialog(
            null,
            mensagem,
            titulo,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );
        
        return Optional.ofNullable(selecionado);
    }
}
