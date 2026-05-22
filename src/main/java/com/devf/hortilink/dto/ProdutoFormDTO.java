package com.devf.hortilink.dto;

import java.time.LocalDate;

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
	private LocalDate dataColheira;
	private Categoria categoria;
	private UnidadeMedida unidadeMedida;
	
}
