package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.CarrinhoResponseDTO;
import com.devf.hortilink.dto.CheckoutRequestDTO;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> obterCarrinho(
			@RequestAttribute("userId") Long compradorId) {
    	if (compradorId == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	CarrinhoResponseDTO dto = carrinhoService.obterCarrinhoAtivo(compradorId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponseDTO> adicionarItem(
    		@RequestAttribute("userId") Long compradorId,
            @RequestParam Long ofertaId,
            @RequestParam Integer quantidade) {
        
    	if (compradorId == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	CarrinhoResponseDTO dto = carrinhoService.adicionarItem(compradorId, ofertaId, quantidade);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarQuantidade(
            @RequestAttribute("userId") Long compradorId,
            @PathVariable Long idItem,
            @RequestParam Integer novaQuantidade) {
        if (compradorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CarrinhoResponseDTO dto = carrinhoService.atualizarQuantidadeItem(compradorId, idItem, novaQuantidade);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> removerItem(
            @RequestAttribute("userId") Long compradorId,
            @PathVariable Long idItem) {
        if (compradorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CarrinhoResponseDTO dto = carrinhoService.removerItem(compradorId, idItem);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> limparCarrinho(@RequestAttribute("userId") Long compradorId) {
        if (compradorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        carrinhoService.limparCarrinho(compradorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> realizarCheckout(
            @RequestAttribute("userId") Long compradorId,
            @RequestBody CheckoutRequestDTO dto) {

        try {
            if (compradorId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Pedido pedidoSalvo = carrinhoService.realizarCheckout(compradorId, dto);

            return ResponseEntity.ok().body("{\"mensagem\": \"Pedido realizado com sucesso!\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }

    }
}