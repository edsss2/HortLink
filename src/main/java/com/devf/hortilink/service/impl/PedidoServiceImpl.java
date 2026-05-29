package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devf.hortilink.dto.PedidoDTO;
import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.enums.StatusPedido;
import com.devf.hortilink.repository.PedidoRepository;
import com.devf.hortilink.service.PedidoService;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Override
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Override
    public List<Pedido> listarHistoricoDoCliente(Long clienteId) {
        return repository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }

    @Override
    public List<PedidoDTO> listarPedidosDoComercio(Long comercioId) {
        List<Pedido> pedidos = repository.findByVendedorIdOrderByDataPedidoDesc(comercioId);
        return pedidos.stream()
				.map(pedido -> new PedidoDTO().fromEntity(pedido))
				.toList();
    }

    @Override
    @Transactional
    public Pedido atualizarStatus(Long idPedido, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(idPedido);
        
        pedido.setStatus(novoStatus);
        
        return repository.save(pedido);
    }
}
