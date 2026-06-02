package com.devf.hortilink.dto;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Endereco;

import lombok.Data;

@Data
public class CompletarPerfilComercioDTO {

	private Long comercioId;
	private String nomeComercio;
	private String telefone;
	private String cep;
	private String cidade;
	private String bairro;
	private String complemento;
	private String estado;
	private String descricao;
	private Double latitude;
	private Double longitude;
	
	public CompletarPerfilComercioDTO fromEntity(ComercioProfile entity) {
		Endereco endereco = entity.getEndereco();
		CompletarPerfilComercioDTO dto = new CompletarPerfilComercioDTO();
		
		dto.setComercioId(entity.getId());
		dto.setTelefone(entity.getTelefone());
		dto.setDescricao(entity.getDescricao());
		dto.setLatitude(endereco.getLatitude());
		dto.setLongitude(endereco.getLongitude());
		
		dto.setCep(endereco.getCep());
		dto.setCidade(endereco.getCidade());
		dto.setBairro(endereco.getBairro());
		dto.setComplemento(endereco.getComplemento());
		dto.setEstado(endereco.getEstado());
		
		return dto;
	}
	
}
