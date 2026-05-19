package com.devf.hortilink.dto;

import java.math.BigDecimal;

import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OfertaDTO {

	private Long id;
	private String nome;
	private BigDecimal preco;
	private BigDecimal promocao;
	private String categoria;
	private String fotoUrl;
	private String descricao;
	private String unidade;
	
	public OfertaDTO fromEntity(Oferta oferta) {
		OfertaDTO dto = new OfertaDTO();
		Produto produto = oferta.getProduto();
		dto.setId(oferta.getId());
		dto.setNome(produto.getNome());
		dto.setPreco(oferta.getValor());
		dto.setPromocao(oferta.getPromocao());
		dto.setCategoria(produto.getCategoria().getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setUnidade(produto.getUnidadeMedida().getLabel());
		
		Foto fotoPrincipal = produto.getFotoPrimaria();
		if (fotoPrincipal != null) {
		    dto.setFotoUrl(fotoPrincipal.getCaminhoArquivo());
		} else {
		    dto.setFotoUrl(null); // ou uma URL de imagem padrão "sem_foto.png"
		}
		
		return dto;
	}
}
