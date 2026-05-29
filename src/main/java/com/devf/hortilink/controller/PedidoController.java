package com.devf.hortilink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.PedidoDTO;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarHistoricoDoCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<PedidoDTO>> listarPorComercio(@PathVariable Long comercioId) {
        List<PedidoDTO> pedidos = pedidoService.listarPedidosDoComercio(comercioId);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Long id, 
            @RequestParam StatusPedido novoStatus) {
        
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}
