package com.devf.hortilink.dto;

import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private String role;
	private Long comercioProfileId;
	private Boolean cadastroIncompleto = false;
	private String urlFotoPerfil;
	
	public static UsuarioDTO fromEntity(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setTelefone(usuario.getTelefone());
		dto.setEmail(usuario.getEmail());
		dto.setRole(usuario.getRole().name());
		
		boolean isVendedor = usuario.getRole() == Role.COMERCIO || usuario.getRole() == Role.PRODUTOR;
		
		if (isVendedor) {
			
			ComercioProfile cp = usuario.getComercioProfile();
			
		    if (cp == null) {
		        dto.cadastroIncompleto = true;
		    } else {
		    	
		    	if(cp.getFotoPerfil() != null) {
		    		dto.setUrlFotoPerfil(cp.getFotoPerfil().getCaminhoArquivo());
		    	}
		    	
		        dto.setComercioProfileId(cp.getId());
		    }
		    
		    
		} else {
			dto.setUrlFotoPerfil(usuario.getFoto().getCaminhoArquivo());
		}

		
		return dto;
	}
	
	
}
