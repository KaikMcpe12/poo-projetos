package src.model;

public class Contato {
    private String telefone;
    private String email;
    
    public Contato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public String getEmail() {
        return email;
    }
}
