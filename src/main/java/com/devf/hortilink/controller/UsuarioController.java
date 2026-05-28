package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.PerfilCompradorDTO;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/perfil")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@PutMapping("/{id}/endereco")
	public ResponseEntity<Void> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
		
		service.atualizarEndereco(id, endereco);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}/foto")
	public ResponseEntity<Void> atualizarEndereco(@PathVariable Long id, @RequestBody Foto foto) {
		
		service.atualizarFoto(id, foto);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<PerfilCompradorDTO> obterPerfil(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		PerfilCompradorDTO dto = service.buscarPerfilPorId(usuario.getId());
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarPerfil(@RequestBody PerfilCompradorDTO dto,
				Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		usuario = service.atualizarPerfil(usuario.getId(), dto);
		
		return ResponseEntity.ok().build();
	}
}
