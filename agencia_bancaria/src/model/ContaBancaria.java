package src.model;

import java.util.Objects;
import java.util.Random;

import src.exception.ContaInexistenteException;
import src.exception.SaldoInsuficienteException;
import src.exception.ValorInvalidoException;

public class ContaBancaria {
    private static int contadorconta = 0;
    private final String numeroConta;
    private final Pessoa titular;
    private double saldo;

    public ContaBancaria(Pessoa titular, double saldoInicial) {
        if (saldoInicial < 0) {
            throw new ValorInvalidoException("Saldo inicial não pode ser negativo");
        }

        this.numeroConta = gerarNumeroConta();
        this.titular = titular;
        this.saldo = saldoInicial;

        contadorconta++;
    }

    private String gerarNumeroConta() {
        Long r =  new Random().nextLong(100000);
        return String.valueOf(r);
    }

    public void sacar(double valor) {
        if(valor <= 0) {
            throw new ValorInvalidoException(
                String.format("Valor inválido: R$%.2f. O saque deve ser maior que zero", valor)
            );
        }
        
        if (valor > this.saldo) {
            throw new SaldoInsuficienteException(
                String.format("Saldo de R$%.2f é insuficiente para o saque de R$%.2f", 
                this.saldo, valor)
            );
        }
        
        this.saldo -= valor;
    }

    public void depositar(double valor) {
        if(valor <= 0) {
            throw new ValorInvalidoException(
                String.format("Valor inválido: R$%.2f. O depósito deve ser maior que zero", valor)
            );
        }

        this.saldo += valor;
    }

    public void transferir(ContaBancaria destino, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException(
                String.format("Valor inválido: R$%.2f. A transferência deve ser maior que zero", valor)
            );
        }
        
        if (destino == null) {
            throw new ContaInexistenteException("Conta destino não pode ser nula");
        }
        
        if (this.equals(destino)) {
            throw new ValorInvalidoException("Não é possível transferir para a mesma conta");
        }
        
        sacar(valor);
        destino.depositar(valor);
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public static int getContadorconta() {
        return contadorconta;
    }

    // pode ser útil mais para frente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return Objects.equals(numeroConta, that.numeroConta);
    }

    @Override
    public String toString() {
        return String.format("Nº %s - CPF: %s - Titular: %s - Saldo: R$%.2f",
            numeroConta, titular.getCPF(), titular.getNome(), saldo);
    }
}
