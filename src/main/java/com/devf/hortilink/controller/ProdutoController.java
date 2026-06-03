package com.devf.hortilink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.ProdutoDTO;
import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.dto.ProdutoListaDTO;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public ResponseEntity<List<ProdutoListaDTO>> listarProdutos(@RequestAttribute("commerceId") Long comercioId) {
		List<ProdutoListaDTO> produtos = service.listarProdutosPorComercio(comercioId);
		
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping("/sem-oferta")
	public ResponseEntity<List<ProdutoListaDTO>> listarProdutosSemOfertaAtiva(@RequestAttribute("commerceId") Long comercioId) {
		List<ProdutoListaDTO> produtos = service.listarProdutosSemOfertaAtiva(comercioId);
		
		return ResponseEntity.ok(produtos);
	}
	
	@PostMapping(value = "/salvar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProdutoDTO> salvarProduto(
			@RequestAttribute("commerceId") Long comercioId,
			@RequestPart("produto") ProdutoFormDTO produtoData,
			@RequestPart("imagem") MultipartFile imagem) {

		if (imagem == null || imagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Imagem é obrigatória");
		}

		Produto produto = service.salvar(comercioId, produtoData, imagem);

		return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.fromEntity(produto));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Produto> atualizarProduto(
			@RequestAttribute("commerceId") Long comercioId,
			@PathVariable Long id,
			@RequestPart("produto") ProdutoFormDTO produtoData,
			@RequestPart(value = "imagem", required = false) MultipartFile imagem) {

		Produto produto = service.atualizar(comercioId, id, produtoData, imagem);

		return ResponseEntity.ok(produto);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		Produto produto = service.buscarPorId(id);
		
		return ResponseEntity.ok(produto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		service.excluirPorId(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/foto")
	public ResponseEntity<Foto> fotoProduto(@PathVariable Long id) {
		Foto foto = service.buscarFotoPorId(id);
		
		return ResponseEntity.ok(foto);
	}
	
}
