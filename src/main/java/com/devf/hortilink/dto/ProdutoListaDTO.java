package com.devf.hortilink.dto;

import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

import lombok.Data;

@Data
public class ProdutoListaDTO {

	private Long id;
	private String nome;
	private Categoria categoria;
	private String imagemUrl;
	private UnidadeMedida unidadeMedida;
	private Boolean ativo;
	
	public static ProdutoListaDTO fromEntity(Produto produto) {
		ProdutoListaDTO dto = new ProdutoListaDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setCategoria(produto.getCategoria());
		dto.setUnidadeMedida(produto.getUnidadeMedida());
		dto.setAtivo(produto.getAtivo());
		
		if (produto.getFoto() != null) {
			dto.setImagemUrl(produto.getFoto().getCaminhoArquivo());
		}
		
		return dto;
	}
}
