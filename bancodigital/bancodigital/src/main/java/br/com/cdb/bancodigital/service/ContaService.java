package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta salvarConta(Cliente cliente){
        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setSaldo(0);
        return contaRepository.save(conta);
    }

    public Conta buscarContaPorId(long id){
        return contaRepository.findById(id).orElse(null);
    }

    public Double depoisarContaPorId(long id, double valor){
        Conta conta = contaRepository.findById(id).orElse(null);
        if(conta != null){
            double saldo = conta.getSaldo();
            saldo = saldo + valor;
            conta.setSaldo(saldo);
            contaRepository.save(conta);
            return saldo;
        }else {
            return null;
        }
    }

    public Double saqueContaPorId(long id, double valor){
        Conta conta = contaRepository.findById(id).orElse(null);
        Double saldo = conta.getSaldo();
        if(saldo > valor ){
            saldo = saldo - valor;
        }
        conta.setSaldo(saldo);
        contaRepository.save(conta);
        return saldo;
    }

    public Double verificarSaldo(Long id){
        Conta conta = contaRepository.findById(id).orElse(null);
        if(conta != null){
            double saldo = conta.getSaldo();
            return saldo;
        }else {
            return null;
        }
    }

}
