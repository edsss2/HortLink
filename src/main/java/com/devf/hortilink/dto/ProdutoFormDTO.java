package com.devf.hortilink.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	private Boolean isOrganico;
	private String categoria;
	private String unidadeMedida;
	private BigDecimal preco;
	private BigDecimal promocao;
	private Integer quantidade;
	
}
