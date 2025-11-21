package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroConta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private double saldo;
    private TipoConta tipoConta;

    public Long getNumero() {
        return numeroConta;
    }

    public void setNumero(Long numero) {
        this.numeroConta = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /*public String getTipoConta() {
        return tipoConta;
    }**/

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

}
