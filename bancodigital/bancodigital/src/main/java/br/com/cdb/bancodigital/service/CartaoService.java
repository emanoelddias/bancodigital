package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.exception.SaldoInsuficienteException;
import br.com.cdb.bancodigital.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao salvarCartao(Conta conta, int senha){
        Cartao cartao = new Cartao();
        cartao.setConta(conta);
        cartao.setSenha(senha);
        return cartaoRepository.save(cartao);
    }

    public Cartao buscarCartaoPorId(long id){
        return cartaoRepository.findById(id).orElse(null);
    }

    public Cartao alterarLimite(long id, double limite){
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        cartao.setLimite(limite);
        cartaoRepository.save(cartao);
        return cartao;
    }

    public Cartao alterarSenha(long id, int senha){
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        cartao.setSenha(senha);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarLimiteDiario(long id, double limite){
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        cartao.setLimiteDiario(limite);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarStatus(long id, String status){
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        if( status.toUpperCase() == "TRUE"){
            cartao.setStatus(true);
            return cartaoRepository.save(cartao);
        } else if (status.toUpperCase() == "FALSE") {
            cartao.setStatus(false);
            return cartaoRepository.save(cartao);
        }else{
            return null;
        }
    }

    public Double consultarFatura(long id){
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        return cartao.getFatura();
    }


    public Cartao pagarFatura(long id, double valorPagamento) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        double valorFatura = cartao.getFatura();
        if(valorFatura >= valorPagamento){
            valorFatura -= valorPagamento;
            cartao.setFatura(valorFatura);
        }else{
            cartao.setFatura(0);
        }

        return cartaoRepository.save(cartao);
    }

    public Cartao realizarPagamento(long id, double valorPagamento) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        double limiteCartao = cartao.getLimite();
        if(limiteCartao >= valorPagamento){
            limiteCartao -= valorPagamento;
            cartao.setLimite(limiteCartao);
        } else{
            throw new SaldoInsuficienteException("O cartão não tem limite suficiente para esta transação.");
        }
        return cartaoRepository.save(cartao);
    }
}
