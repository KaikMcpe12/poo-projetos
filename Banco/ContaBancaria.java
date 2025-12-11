import errors.ContaInexistenteException;
import errors.SaldoInsuficienteException;
import errors.ValorInvalidoException;

import java.util.Objects;
import java.util.Random;

public class ContaBancaria {
    private static int contadorconta = 0;
    private String numeroConta;
    private Pessoa titular;
    private double saldo;

    public ContaBancaria(Pessoa titular, double saldoInicial) {
        this.numeroConta = gerarNumeroConta();
        this.titular = titular;
        this.saldo = saldoInicial;
        contadorconta++;
    }

    public static int getContadorconta() {
        return contadorconta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public String getNumeroConta() {
        return this.numeroConta;
    }

    public String gerarNumeroConta() {
        Long r =  new Random().nextLong(100000);
        return String.valueOf(r);
    }

    // método que valida e lança exceções de negócio ou argumento
    public void sacar(double valor) {
        if (valor <= 0) {
            // lançamento de exceção personalizada para erro de argumento
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        if (valor > this.saldo) {
            // lançamento da exceção de negócio personalizada
            throw new SaldoInsuficienteException(String.format("Saldo de R$%.2f é insuficiente para o saque de R$%.2f.", this.saldo, valor));
        }

        // se o fluxo chegar aqui, o saque é válido
        this.saldo -= valor;
    }

    // metodo que valida o deposito
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        this.saldo += valor;
    }

    public void transferir(ContaBancaria destino, double valor) throws ContaInexistenteException {
        if (valor <= 0) {
            throw new ValorInvalidoException(String.format("Valor inválido: %.2f.", valor));
        }

        // "Objects.equals(getId(), " ";" é a mesma coisa que destino.getId() == " " e retorna true se o id for igual a uma String vazia e false se possuir algum conteudo;
        if (Objects.equals(destino.getNumeroConta(), " ")) {
            throw new ContaInexistenteException("Conta destino inexistente");
        }

        sacar(valor);
        destino.depositar(valor);
    }


    @Override
    public String toString() {
        return String.format(
            "n° %s - CPF: %s - Titular: %s",
            numeroConta, titular.getCPF(), titular.getNome());
    }
}
