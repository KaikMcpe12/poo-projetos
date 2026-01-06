package src.util;

// util para validar dados
public class ValidadorUtil {
    // usa regex (assumo que peguei um modelo de REGEX pronto para email e cpf)
    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
    
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 4;
    }
    
    public static boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }
}
