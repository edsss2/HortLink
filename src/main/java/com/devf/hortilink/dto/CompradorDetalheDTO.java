package com.devf.hortilink.dto;

import lombok.Data;

@Data
public class CompradorDetalheDTO {

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	
	private String cep;
	private String cidade;
	private String estado;
}
