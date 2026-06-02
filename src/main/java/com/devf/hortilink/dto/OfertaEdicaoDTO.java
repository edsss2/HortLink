package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.devf.hortilink.entity.Oferta;

import lombok.Data;

@Data
public class OfertaEdicaoDTO {

	private Long ofertaId;
	private BigDecimal preco;
	private BigDecimal estoqueAtual;
	private LocalDate dataColheita;
	private Boolean disponivelParaVenda;
	
	private Long produtoId;
	private String nomeProduto;
	private String unidadeSimbolo;
	private String imageUrl;
	
	public static OfertaEdicaoDTO fromEntity(Oferta oferta) {
	    OfertaEdicaoDTO dto = new OfertaEdicaoDTO();
	    dto.setOfertaId(oferta.getId());
	    dto.setPreco(oferta.getValor());
	    dto.setEstoqueAtual(oferta.getEstoqueAtual());
	    dto.setDataColheita(oferta.getDataColheita());
	    dto.setDisponivelParaVenda(oferta.getDisponivelParaVenda());
	    
	    if (oferta.getProduto() != null) {
	        dto.setProdutoId(oferta.getProduto().getId());
	        dto.setNomeProduto(oferta.getProduto().getNome());
	        dto.setUnidadeSimbolo(oferta.getProduto().getUnidadeMedida().getSimbolo());
	        if (oferta.getProduto().temFoto()) {
	            dto.setImageUrl(oferta.getProduto().getFoto().getCaminhoArquivo());
	        }
	    }
	    
	    return dto;
	}
}
