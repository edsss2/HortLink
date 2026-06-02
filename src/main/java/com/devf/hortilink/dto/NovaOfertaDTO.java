package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devf.hortilink.entity.Oferta;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NovaOfertaDTO {

	private Long produtoId;
	private BigDecimal preco;
	private BigDecimal estoqueAtual;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataColheita;
	private Boolean disponivelParaVenda;
	
	public static NovaOfertaDTO fromEntity(Oferta oferta) {
	    NovaOfertaDTO dto = new NovaOfertaDTO();
	    dto.setProdutoId(oferta.getProduto().getId());
	    dto.setPreco(oferta.getValor());
	    dto.setEstoqueAtual(oferta.getEstoqueAtual());
	    dto.setDataColheita(oferta.getDataColheita());
	    dto.setDisponivelParaVenda(oferta.getDisponivelParaVenda());
	    
	    return dto;
	}
}
