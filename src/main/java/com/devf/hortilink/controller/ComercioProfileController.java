package com.devf.hortilink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<ComercioProfile> buscarPorId(@PathVariable Long id) {
		ComercioProfile produto = service.buscarPorId(id);
		
		return ResponseEntity.ok(produto);
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
	
	
}
