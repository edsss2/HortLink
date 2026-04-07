package com.devf.hortilink.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioDTO {
	
	private Long id;
	
	private String nome;
	private Integer raioMaximoEntregaKm;
	private String rua;
	private String estado;
	private String numero;
	private String cep;
	private String complemento;
	private String cidade;
	private String bairro;
	
}
