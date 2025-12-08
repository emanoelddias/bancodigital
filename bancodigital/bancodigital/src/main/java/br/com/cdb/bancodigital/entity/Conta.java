package br.com.cdb.bancodigital.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroConta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartaos = new ArrayList<>();

    private TipoConta tipo;

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

    public void setTipoConta(String tipoConta) {
        this.tipoConta = TipoConta.valueOf(tipoConta.toUpperCase());
    }

    public String getTipoConta() {
        return tipoConta.name();
    }

}
