package br.com.cdb.bancodigital.service;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(String nome,Long cpf){
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarCliente(){
        return  clienteRepository.findAll();
    }

    public Cliente buscarCliente(Long id){
        return clienteRepository.findById(id).orElse(null);
    }

    public void excluirCliente(Long id){
        clienteRepository.deleteById(id);
    }

    public Cliente atualizarCliente(Long id, Cliente cliente){
        Cliente clienteAtual = clienteRepository.findById(id).orElse(null);
        if(clienteAtual != null){
            clienteAtual.setNome(cliente.getNome());
            clienteAtual.setCpf(cliente.getCpf());
            return clienteRepository.save(clienteAtual);
        } else {
            return null;
        }
    }
}
