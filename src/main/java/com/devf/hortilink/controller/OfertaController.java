package com.devf.hortilink.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.ProdutoCardDTO;
import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.entity.Oferta;
import com.devf.hortilink.service.OfertaService;

@RestController
@RequestMapping("/oferta")
public class OfertaController {

	@Autowired
	private OfertaService service;

	@GetMapping("/listar")
	public ResponseEntity<List<ProdutoCardDTO>> Listar() {
		List<ProdutoCardDTO> produtosCard = service.transformOfertas(service.listarTodos());
		for(ProdutoCardDTO pcd : produtosCard) {
			System.out.println(pcd.getImageUrl());
		}
		return ResponseEntity.ok(produtosCard);
	}
	
	@GetMapping
    public ResponseEntity<List<OfertaDTO>> listarOfertas() {
        List<OfertaDTO> ofertas = service.listarOfertasParaApp();
        
        return ResponseEntity.ok(ofertas);
    }

	@PostMapping("/carrinho")
	public ResponseEntity<List<ProdutoCardDTO>> listarCarrinho(@RequestBody List<Long> ids) {
		List<ProdutoCardDTO> produtosCard = service.transformOfertas(service.buscarCarrinho(ids));
		return ResponseEntity.ok(produtosCard);
	}

	@PostMapping("/atualizar")
	public ResponseEntity<Oferta> atualizarOferta(@RequestBody Oferta oferta) {
		Oferta salvo = service.atualizar(oferta);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getId())
				.toUri();

		return ResponseEntity.created(location).body(salvo);
	}

	@PostMapping("/salvar")
	public ResponseEntity<Void> salvar(@RequestPart("produto") ProdutoFormDTO produtoData, 
	        @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
	        @RequestPart(value = "imagensAdicionais", required = false) List<MultipartFile> imagensAdicionais,
	        Principal principal) {
		String emailUsuario = principal.getName();
		
		service.salvarNovoProduto(emailUsuario, produtoData, imagemPrincipal, imagensAdicionais);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Oferta> buscarPorId(@PathVariable Long id) {
		Oferta produto = service.buscarPorId(id);

		return ResponseEntity.ok(produto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPorId(@PathVariable Long id) {
		service.excluirPorId(id);

		return ResponseEntity.noContent().build();
	}

}
