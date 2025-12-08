package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.exception.SaldoInsuficienteException;
import br.com.cdb.bancodigital.service.CartaoService;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/cartoes"})
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Cartao> cadastrar(@RequestBody long id,@RequestBody int senha){
        Conta conta = contaService.buscarContaPorId(id);
        if(conta != null ){
           Cartao cartaoSalvo = cartaoService.salvarCartao(conta, senha);
           return ResponseEntity.ok(cartaoSalvo);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscarCartao(@PathVariable long id){
        Cartao cartao =  cartaoService.buscarCartaoPorId(id);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/limite")
    public ResponseEntity<Cartao> alterarLimite(@PathVariable long id, @RequestBody long limite){
        Cartao cartao = cartaoService.alterarLimite(id, limite);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Cartao> alterarSenha(@PathVariable long id, @RequestBody int senha){
        Cartao cartao = cartaoService.alterarSenha(id, senha);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/limite-diario")
    public ResponseEntity<Cartao>  alterarLimiteDiario(@PathVariable long id, @RequestBody double limite){
        Cartao cartao = cartaoService.alterarLimiteDiario(id, limite);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cartao>  alterarStatus(@PathVariable long id, @RequestBody String status){
        Cartao cartao = cartaoService.alterarStatus(id, status);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/fatura")
    public ResponseEntity<Double> consultarFatura(@PathVariable long id){
        Cartao cartao = cartaoService.buscarCartaoPorId(id);
        if(cartao != null){
            return ResponseEntity.ok(cartaoService.consultarFatura(id));
        }else   {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/fatura/pagamento")
    public ResponseEntity<Cartao> pagarFatura(@PathVariable long id, @RequestBody double valorPagamento){
        Cartao cartao = cartaoService.pagarFatura(id,valorPagamento);
        if(cartao != null){
            return ResponseEntity.ok(cartao);
        }else   {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/pagamento")
    public ResponseEntity<?> realizarPagamento(@PathVariable long id, @RequestBody double valorPagamento){

        Cartao cartao = cartaoService.buscarCartaoPorId(id);

        if(cartao != null){
            try{
                cartaoService.realizarPagamento(id,valorPagamento);
                return ResponseEntity.ok(cartao);
            } catch(SaldoInsuficienteException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }else   {
            return ResponseEntity.notFound().build();
        }
    }
}
