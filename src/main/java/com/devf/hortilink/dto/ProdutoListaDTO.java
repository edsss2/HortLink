package com.devf.hortilink.dto;

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
}
