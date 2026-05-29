package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.PerfilCompradorDTO;
import com.devf.hortilink.entity.Endereco;
import com.devf.hortilink.entity.Foto;
// ...existing code...
import com.devf.hortilink.service.UsuarioService;

// ...existing code...

@RestController
@RequestMapping("/usuarios")
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
	
	@GetMapping("/perfil")
	public ResponseEntity<PerfilCompradorDTO> obterPerfil(@RequestAttribute("userId") Long userId) {
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		PerfilCompradorDTO dto = service.buscarPerfilPorId(userId);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/perfil")
	public ResponseEntity<Void> atualizarPerfil(@RequestBody PerfilCompradorDTO dto,
				@RequestAttribute("userId") Long userId) {
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		service.atualizarPerfil(userId, dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/detalhes-cliente/{clienteId}")
	public ResponseEntity<PerfilCompradorDTO> obterDetalhesCliente(@RequestAttribute("commerceId") Long comercioId, @PathVariable Long clienteId) {

		if (comercioId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		PerfilCompradorDTO dto = service.buscarPerfilClientePorId(clienteId, comercioId);
		return ResponseEntity.ok(dto);
	}
}
