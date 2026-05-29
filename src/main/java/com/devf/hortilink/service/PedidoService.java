package com.devf.hortilink.service;

import java.util.List;

import com.devf.hortilink.dto.PedidoDTO;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.enums.StatusPedido;

public interface PedidoService {
    
    Pedido buscarPorId(Long id);
    
    List<Pedido> listarHistoricoDoCliente(Long clienteId);
    
    List<PedidoDTO> listarPedidosDoComercio(Long comercioId);
    
    Pedido atualizarStatus(Long idPedido, StatusPedido novoStatus);
}
