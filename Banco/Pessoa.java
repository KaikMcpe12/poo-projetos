public class Pessoa {
    private static int contador = 0;
    private String nome;
    private int senha;
    private String CPF;
    private String email;

    public Pessoa(String nome, int senha, String CPF, String email) {
        this.nome = nome;
        this.senha = senha;
        this.CPF = CPF;
        this.email = email;
        
        contador++;
    }

    public static int getContador() {
        return contador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }


    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }
}
