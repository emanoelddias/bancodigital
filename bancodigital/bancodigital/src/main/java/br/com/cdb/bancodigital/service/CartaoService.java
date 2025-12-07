package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
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

}
