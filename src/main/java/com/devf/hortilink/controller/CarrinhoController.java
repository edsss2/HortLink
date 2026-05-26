package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.CarrinhoResponseDTO;
import com.devf.hortilink.entity.Carrinho;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.service.CarrinhoService;

@RestController
@RequestMapping("{compradorId}/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> obterCarrinho(@PathVariable Long compradorId) {
    	CarrinhoResponseDTO dto = carrinhoService.obterCarrinhoAtivo(compradorId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponseDTO> adicionarItem(
            @PathVariable Long compradorId,
            @RequestParam Long ofertaId,
            @RequestParam Integer quantidade) {
        
    	CarrinhoResponseDTO dto = carrinhoService.adicionarItem(compradorId, ofertaId, quantidade);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarQuantidade(
            @PathVariable Long compradorId,
            @PathVariable Long idItem,
            @RequestParam Integer novaQuantidade) {
        
    	CarrinhoResponseDTO dto = carrinhoService.atualizarQuantidadeItem(compradorId, idItem, novaQuantidade);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> removerItem(
            @PathVariable Long compradorId,
            @PathVariable Long idItem) {
        
    	CarrinhoResponseDTO dto = carrinhoService.removerItem(compradorId, idItem);
        return ResponseEntity.ok(dto);
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