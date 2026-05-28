package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.CarrinhoResponseDTO;
import com.devf.hortilink.dto.CheckoutRequestDTO;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> obterCarrinho(
    		@PathVariable Long compradorId,
    		Authentication authentication) {
    	Usuario usuario = (Usuario) authentication.getPrincipal();
    	CarrinhoResponseDTO dto = carrinhoService.obterCarrinhoAtivo(compradorId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponseDTO> adicionarItem(
    		Authentication authentication,
            @RequestParam Long ofertaId,
            @RequestParam Integer quantidade) {
        
    	Usuario usuario = (Usuario) authentication.getPrincipal();
    	CarrinhoResponseDTO dto = carrinhoService.adicionarItem(usuario.getId(), ofertaId, quantidade);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> atualizarQuantidade(
    		Authentication authentication,
            @PathVariable Long idItem,
            @RequestParam Integer novaQuantidade) {
        
    	Usuario usuario = (Usuario) authentication.getPrincipal();
    	CarrinhoResponseDTO dto = carrinhoService.atualizarQuantidadeItem(usuario.getId(), idItem, novaQuantidade);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/itens/{idItem}")
    public ResponseEntity<CarrinhoResponseDTO> removerItem(
    		Authentication authentication,
            @PathVariable Long idItem) {
        
    	Usuario usuario = (Usuario) authentication.getPrincipal();
    	CarrinhoResponseDTO dto = carrinhoService.removerItem(usuario.getId(), idItem);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> limparCarrinho(Authentication authentication) {
    	Usuario usuario = (Usuario) authentication.getPrincipal();
        carrinhoService.limparCarrinho(usuario.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> realizarCheckout(
    		Authentication authentication,
            @RequestBody CheckoutRequestDTO dto) {
    	
    	try {
    		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
    		
    		Pedido pedidoSalvo = carrinhoService.realizarCheckout(usuarioLogado.getId(), dto);
    		
    		return ResponseEntity.ok().body("{\"mensagem\": \"Pedido realizado com sucesso!\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
        
    }
}