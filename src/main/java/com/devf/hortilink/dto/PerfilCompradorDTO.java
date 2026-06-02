package com.devf.hortilink.dto;

import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Usuario;

import lombok.Data;

@Data
public class PerfilCompradorDTO {

	private String telefone;
    private String cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String complemento;
    
    public PerfilCompradorDTO fromEntity(Usuario usuario) {
    	PerfilCompradorDTO dto = new PerfilCompradorDTO();
    	Endereco endereco = usuario.getEndereco();
    	
    	dto.setTelefone(usuario.getTelefone());
    	dto.setCep(endereco.getCep());
    	dto.setCidade(endereco.getCidade());
    	dto.setEstado(endereco.getEstado());
    	dto.setBairro(endereco.getBairro());
    	dto.setComplemento(endereco.getComplemento());
    	
    	return dto;
    }
}
 