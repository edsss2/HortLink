package com.devf.hortilink.dto;

import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.enums.Categoria;
import com.devf.hortilink.enums.UnidadeMedida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoFormDTO {

	private Long id;
	private String nome;
	private String descricao;
	private Categoria categoria;
	private UnidadeMedida unidadeMedida;
	
	public static ProdutoFormDTO fromEntity(Produto produto) {
	    ProdutoFormDTO dto = new ProdutoFormDTO();
	    dto.setId(produto.getId());
	    dto.setNome(produto.getNome());
	    dto.setDescricao(produto.getDescricao());
	    dto.setCategoria(produto.getCategoria());
	    dto.setUnidadeMedida(produto.getUnidadeMedida());
	    
	    return dto;
	}
	
}
