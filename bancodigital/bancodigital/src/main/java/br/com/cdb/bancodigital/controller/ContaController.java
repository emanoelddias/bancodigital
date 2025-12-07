package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.service.ClienteService;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
#### Conta
- **POST /contas/{id}/transferencia** - Realizar uma transferência entre contas
- **POST /contas/{id}/pix**           - Realizar um pagamento via Pix
- **PUT  /contas/{id}/manutencao**    - Aplicar taxa de manutenção mensal (para conta corrente)
- **PUT  /contas/{id}/rendimentos**   - Aplicar rendimentos (para conta poupança)
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

}
