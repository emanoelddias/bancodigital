package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.exception.ContaNaoEncontradaException;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
#### Conta
- **POST /contas/{id}/pix**           - Realizar um pagamento via Pix
*/

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/{id}/{tipoConta}")
    public ResponseEntity<Conta> adicionarConta(@PathVariable Long id, @PathVariable String tipoConta){
        Cliente cliente = clienteService.buscarCliente(id);
        if (cliente != null){
            Conta contaSalva = contaService.salvarConta(cliente, tipoConta);
            return ResponseEntity.ok(contaSalva);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarConta(@PathVariable Long id){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            return ResponseEntity.ok(conta);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> verificarSaldo(@PathVariable Long id){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            return ResponseEntity.ok(conta.getSaldo());
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Double> depositar(@PathVariable Long id, @RequestBody double valor){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            contaService.depoisarContaPorId(id, valor);
            return  ResponseEntity.ok(contaService.verificarSaldo(id));
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/saque")
    public  ResponseEntity<Double> saque(@PathVariable Long id, @RequestBody double valor){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            return ResponseEntity.ok(contaService.saqueContaPorId(id, valor));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/transferencia")
    public ResponseEntity<?> transferir(@PathVariable Long id, @RequestBody Long idtranferencia , @RequestBody double valor){

        Conta conta = contaService.buscarContaPorId(id);

        try{
            contaService.transferencia(id, idtranferencia , valor);
            return ResponseEntity.ok(conta);
        } catch (ContaNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<?> manutencao(@PathVariable Long id){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            return ResponseEntity.ok(contaService.manutencao(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/rendimentos")
    public ResponseEntity<?> rendimentos(@PathVariable Long id){
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null){
            return ResponseEntity.ok(contaService.rendimentos(id));
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}
