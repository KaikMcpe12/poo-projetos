package src.model;

import src.utils.enums.Status;
import src.utils.enums.TipoLoja;

// TODO: pedir dados de endereço, tipoLoja, status e de contato no view
public class Loja {

    private final String cnpj;
    private String nome;
    private Endereco endereco;
    private TipoLoja tipoLoja;
    private Contato contato;
    private Status status;
    private String motivoStatus;
    private int operacoesRealizadas;
    private int caixa = 1; // teste (remover depoois)

    public Loja(
        String nome,
        Endereco endereco,
        TipoLoja tipoLoja,
        Contato contato,
        String cnpj
    ) {
        this.nome = nome;
        this.endereco = endereco;
        this.cnpj = cnpj;
        this.tipoLoja = tipoLoja;
        this.contato = contato;
        this.status = Status.ATIVA;
        this.operacoesRealizadas = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public TipoLoja getTipoLoja() {
        return tipoLoja;
    }

    public void setTipoLoja(TipoLoja tipoLoja) {
        this.tipoLoja = tipoLoja;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status, String motivo) {
        this.status = status;
        this.motivoStatus = motivo;
    }

    public String getMotivoStatus() {
        return motivoStatus;
    }

    public int getOperacoesRealizadas() {
        return operacoesRealizadas;
    }

    public boolean deveSerBloqueada() {
        return operacoesRealizadas >= 3 && caixa == 0.0;
    }

    public boolean isAtiva() {
        return status.equals(Status.ATIVA);
    }

    public void adicionarAoCaixa(double valor) { // ainda vou implementar
        this.caixa += valor;
        this.operacoesRealizadas++;
    }
}
