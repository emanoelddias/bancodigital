package br.com.cdb.bancodigital.controller;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.service.CartaoService;
import br.com.cdb.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/cartoes"})
public class CartaoController {

     /*
        - **POST /cartoes/{id}/pagamento** - Realizar um pagamento com o cartão
        - **PUT /cartoes/{id}/status** - Ativar ou desativar um cartão
        - **GET /cartoes/{id}/fatura** - Consultar fatura do cartão de crédito
        - **POST /cartoes/{id}/fatura/pagamento** - Realizar pagamento da fatura do cartão de crédito
        - **PUT /cartoes/{id}/limite-diario** - Alterar limite diário do cartão de débito
    */

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



}
