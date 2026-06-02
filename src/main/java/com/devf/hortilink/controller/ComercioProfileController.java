package com.devf.hortilink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.ComercioDTO;
import com.devf.hortilink.dto.CompletarPerfilComercioDTO;
import com.devf.hortilink.dto.DashboardDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.service.ComercioProfileService;
import com.devf.hortilink.service.DashboardService;

@RestController
@RequestMapping("/comercio")
public class ComercioProfileController {

	@Autowired
	private ComercioProfileService service;
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ComercioProfile>> ListarComercios() {
		List<ComercioProfile> comercios = service.listarTodos();
		
		return ResponseEntity.ok(comercios);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		service.excluirPorId(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/dashboard/{comercioId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long comercioId) {
        return ResponseEntity.ok(dashboardService.obterDadosDashboard(comercioId));
    }
	
	@GetMapping
	public ResponseEntity<ComercioDTO> getComercio(@RequestAttribute("commerceId") Long comercioId) {
		ComercioDTO dto = service.buscarPorIdDTO(comercioId);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ComercioDTO> getComercioPorId(@PathVariable Long id) {
		ComercioDTO dto = service.buscarPorIdDTO(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/detalhes")
	public ResponseEntity<CompletarPerfilComercioDTO> getPerfilComercio(@RequestAttribute("commerceId") Long comercioId) {
		CompletarPerfilComercioDTO dto = service.buscarPerfilComercioPorId(comercioId);
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/completar-perfil")
	public ResponseEntity<Void> completarPerfil(@RequestBody CompletarPerfilComercioDTO dto, 
			@RequestAttribute("commerceId") Long comercioId, 
			@RequestAttribute("userId") Long userId) {
		dto.setComercioId(comercioId);
		service.completarPerfil(dto, userId);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/listar/{cidade}")
	public ResponseEntity<List<ComercioDTO>> listarPorCidade(@PathVariable String cidade) {
		List<ComercioDTO> comercios = service.listarPorCidade(cidade);
		
		return ResponseEntity.ok(comercios);
	}
	
	
}
