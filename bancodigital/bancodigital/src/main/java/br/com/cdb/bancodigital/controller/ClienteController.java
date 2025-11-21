package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/add")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente clienteAdicionado = clienteService.salvarCliente(cliente.getNome(), cliente.getCpf());
        if(clienteAdicionado != null){
            return ResponseEntity.ok("Cliente " + clienteAdicionado.getNome() + "  adicionado com sucesso " + HttpStatus.CREATED);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Cliente>> listarCliente(){
        List<Cliente> listaClientes = clienteService.listarCliente();
        return  ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id){
        Cliente cliente =  clienteService.buscarCliente(id);
        if(cliente != null){
            return ResponseEntity.ok(cliente);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id){
        Cliente cliente = clienteService.buscarCliente(id);
        if(cliente != null){
            clienteService.excluirCliente(id);
            return ResponseEntity.ok("Cliente " + cliente.getNome() + " deletado com sucesso");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        if(clienteAtualizado != null){
            return ResponseEntity.ok(clienteAtualizado);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
