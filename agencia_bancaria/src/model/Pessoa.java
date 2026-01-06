package src.model;

import java.util.Objects;

public class Pessoa {
    private static int contador = 0;
    private String nome;
    private String senha;
    private String CPF;
    private String email;

    public Pessoa(String nome, String senha, String CPF, String email) {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(CPF, pessoa.CPF);
    }
}
