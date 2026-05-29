package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.ItemPedido;

import lombok.Data;

@Data
public class ItemPedidoDTO {
	
	private Long id;
	private String nomeProduto;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	
	public ItemPedidoDTO fromEntity(ItemPedido item) {
		ItemPedidoDTO dto = new ItemPedidoDTO();
		dto.setId(item.getId());
		dto.setNomeProduto(item.getNomeProduto());
		dto.setQuantidade(item.getQuantidade());
		dto.setPrecoUnitario(item.getPrecoUnitario());
		return dto;
	}
}