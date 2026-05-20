package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.service.CarrinhoService;

@RestController
@RequestMapping("{compradorId}/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<Carrinho> obterCarrinho(@PathVariable Long compradorId) {
        Carrinho carrinho = carrinhoService.obterCarrinhoAtivo(compradorId);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/itens")
    public ResponseEntity<Carrinho> adicionarItem(
            @PathVariable Long compradorId,
            @RequestParam Long ofertaId,
            @RequestParam Integer quantidade) {
        
        Carrinho carrinho = carrinhoService.adicionarItem(compradorId, ofertaId, quantidade);
        return ResponseEntity.ok(carrinho);
    }

    @PutMapping("/itens/{idItem}")
    public ResponseEntity<Carrinho> atualizarQuantidade(
            @PathVariable Long compradorId,
            @PathVariable Long idItem,
            @RequestParam Integer novaQuantidade) {
        
        Carrinho carrinho = carrinhoService.atualizarQuantidadeItem(compradorId, idItem, novaQuantidade);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping("/itens/{idItem}")
    public ResponseEntity<Carrinho> removerItem(
            @PathVariable Long compradorId,
            @PathVariable Long idItem) {
        
        Carrinho carrinho = carrinhoService.removerItem(compradorId, idItem);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping
    public ResponseEntity<Void> limparCarrinho(@PathVariable Long compradorId) {
        carrinhoService.limparCarrinho(compradorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Pedido> realizarCheckout(
            @PathVariable Long compradorId,
            @RequestParam String formaPagamento) {
        
        Pedido pedido = carrinhoService.realizarCheckout(compradorId, formaPagamento);
        return ResponseEntity.ok(pedido);
    }
}