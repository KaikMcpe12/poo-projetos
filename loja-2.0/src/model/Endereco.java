package src.model;

public class Endereco {
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    
    public Endereco(String cep, String estado, String cidade, String bairro, String rua, String numero) {
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }
    
    public String getCep() {
        return cep;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public String getRua() {
        return rua;
    }
    
    public String getNumero() {
        return numero;
    }
}
