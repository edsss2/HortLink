package com.devf.hortilink.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.ComercioDTO;
import com.devf.hortilink.dto.DashboardDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.service.ComercioProfileService;
import com.devf.hortilink.service.DashboardService;
import com.devf.hortilink.service.UsuarioService;

@RestController
@RequestMapping("/comercio")
public class ComercioProfileController {

	@Autowired
	private ComercioProfileService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ComercioProfile>> ListarComercios() {
		List<ComercioProfile> comercios = service.listarTodos();
		
		return ResponseEntity.ok(comercios);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Void> salvarComercio(@RequestBody ComercioDTO comercio, Principal principal) {
		Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
		ComercioProfile salvo = service.salvar(comercio, usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
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
	
	@GetMapping("/{id}/fotos")
	public ResponseEntity<List<Foto>> fotosComercio(@PathVariable Long id) {
		List<Foto> fotos = service.buscarFotosPorId(id);
		
		return ResponseEntity.ok(fotos);
	}
	
	@GetMapping("/{id}/ofertas")
	public ResponseEntity<List<Oferta>> ofertasComercio(@PathVariable Long id) {
		List<Oferta> ofertas = service.buscarOfertasPorId(id);
		
		return ResponseEntity.ok(ofertas);
	}
	
	
	@GetMapping("/dashboard/{comercioId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long comercioId) {
        return ResponseEntity.ok(dashboardService.obterDadosDashboard(comercioId));
    }
	
	
}
