package com.devf.hortilink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.DetalheOfertaDTO;
import com.devf.hortilink.dto.NovaOfertaDTO;
import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.OfertaEdicaoDTO;
import com.devf.hortilink.service.OfertaService;

@RestController
@RequestMapping("/oferta")
public class OfertaController {

	@Autowired
	private OfertaService service;

	@GetMapping
    public ResponseEntity<List<OfertaDTO>> listarOfertas() {
        List<OfertaDTO> ofertas = service.listarOfertasParaApp();
        
        return ResponseEntity.ok(ofertas);
    }
	
	@GetMapping("/comercio/{comercioId}")
	public ResponseEntity<List<OfertaDTO>> listarOfertasDoComercio(@PathVariable Long comercioId) {
		List<OfertaDTO> ofertas = service.buscarOfertasPorComercioId(comercioId);
		
		return ResponseEntity.ok(ofertas);
	}
	
	@GetMapping("/comercio")
	public ResponseEntity<List<OfertaDTO>> listarOfertasDoProprioComercio(@RequestAttribute("commerceId") Long comercioId) {
		List<OfertaDTO> ofertas = service.buscarOfertasPorComercioId(comercioId);
		
		return ResponseEntity.ok(ofertas);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<OfertaDTO> salvarOferta(@RequestAttribute("commerceId") Long comercioId, @RequestBody NovaOfertaDTO dto) {
		OfertaDTO salvo = service.salvar(comercioId, dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}
	

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<OfertaDTO> atualizarOferta(@RequestAttribute("commerceId") Long comercioId, @PathVariable Long id, @RequestBody NovaOfertaDTO dto) {
		OfertaDTO atualizado = service.atualizar(comercioId, id, dto);

		return ResponseEntity.ok(atualizado);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		service.excluirPorId(id);

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/detalhes/{id}")
	public ResponseEntity<DetalheOfertaDTO> buscarOfertaDetalhadaPorId(@PathVariable Long id) {
		DetalheOfertaDTO dto = service.buscarOfertaDetalhadaPorId(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/edicao/{id}")
	public ResponseEntity<OfertaEdicaoDTO> buscarOfertaEdicaoPorId(@RequestAttribute("commerceId") Long comercioId, @PathVariable Long id) {
		OfertaEdicaoDTO dto = service.buscarOfertaEdicaoPorId(comercioId, id);
		
		return ResponseEntity.ok(dto);
	}

}
