package com.devf.hortilink.dto;

import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.enums.Role;

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
			
		    if (usuario.getComercioProfile() == null) {
		        dto.cadastroIncompleto = true;
		    } else {
		        dto.setComercioProfileId(usuario.getComercioProfile().getId());
		        dto.setUrlFotoPerfil(usuario.getComercioProfile().getFotoPerfil().getCaminhoArquivo());
		    }
		    
		    
		} else {
			dto.setUrlFotoPerfil(usuario.getFoto().getCaminhoArquivo());
		}

		
		return dto;
	}
	
	
	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}
	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getComercioProfileId() {
		return comercioProfileId;
	}
	public void setComercioProfileId(Long comercioProfileId) {
		this.comercioProfileId = comercioProfileId;
	}
	public Boolean getCadastroIncompleto() {
		return cadastroIncompleto;
	}
	public void setCadastroIncompleto(Boolean cadastroIncompleto) {
		this.cadastroIncompleto = cadastroIncompleto;
	}	
	
	
}
