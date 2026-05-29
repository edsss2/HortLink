package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.devf.hortilink.entity.Pedido;
import com.devf.hortilink.enums.StatusPedido;

import lombok.Data;

@Data
public class PedidoDTO {
	
	private String id;
	private Long clienteId;
	private StatusPedido status;
	private BigDecimal valorTotal;
	private String formaPagamento;
	private String observacoes;
	private List<ItemPedidoDTO> itens;
	
	public PedidoDTO fromEntity(Pedido pedido) {
		PedidoDTO dto = new PedidoDTO();
		dto.setId(pedido.getCodigo());
		dto.setClienteId(pedido.getCliente().getId());
		dto.setStatus(pedido.getStatus());
		dto.setValorTotal(pedido.getValorTotal());
		dto.setFormaPagamento(pedido.getFormaPagamento());
		dto.setObservacoes(pedido.getObservacoes());
		dto.setItens(pedido.getItens().stream()
				.map(item -> new ItemPedidoDTO().fromEntity(item))
				.collect(Collectors.toList()));
		return dto;
	}
}
