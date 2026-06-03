package com.devf.hortilink.dto;

import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

import lombok.Data;


@Data
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private UnidadeMedida unidadeMedida;
    private String imagemUrl;
    public double distanciaKm = Double.MAX_VALUE;
    public Long vendedorId;
    
    public static ProdutoDTO fromEntity(com.devf.hortilink.entity.Produto produto) {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setCategoria(produto.getCategoria());
		dto.setUnidadeMedida(produto.getUnidadeMedida());
		if (produto.getFoto() != null) {
			dto.setImagemUrl(produto.getFoto().getCaminhoArquivo());
		}
		if (produto.getComercio() != null) {
			dto.setVendedorId(produto.getComercio().getId());
		}
		return dto;
	}
}
