package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.dto.ProdutoListaDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.repository.ProdutoRepository;
import com.devf.hortilink.service.FotoService;
import com.devf.hortilink.service.ProdutoService;
import com.devf.hortilink.service.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private FotoService fotoService;

	@Override
	public List<ProdutoListaDTO> listarProdutosPorComercio(Long comercioId) {
		List<Produto> produtos = repository.findByComercioId(comercioId);
	    
		return produtos.stream()
	            .map(ProdutoListaDTO::fromEntity)
	            .toList();
	}

	@Override
	public Produto buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + id));
	}

	@Override
	public Produto excluirPorId(Long id) {
		Produto produto = buscarPorId(id);
		repository.delete(produto);
		return produto;
	}

	@Override
	@Transactional
	public Produto salvar(Long comercioid, ProdutoFormDTO formData, MultipartFile imagem) {
		Produto produto = new Produto();
		produto.setNome(formData.getNome());
		produto.setDescricao(formData.getDescricao());
		produto.setCategoria(formData.getCategoria());
		produto.setUnidadeMedida(formData.getUnidadeMedida());
		
		ComercioProfile comercioProfile = new ComercioProfile();
		comercioProfile.setId(comercioid);
		produto.setComercio(comercioProfile);

		Produto produtoSalvo = repository.save(produto);

		Foto foto = fotoService.salvarFotoProduto(imagem, produtoSalvo);
		produtoSalvo.setFoto(foto);

		return repository.save(produtoSalvo);
	}

	@Override
	@Transactional
	public Produto atualizar(Long comercioId, Long id, ProdutoFormDTO formData, MultipartFile imagem) {
		Produto produto = buscarPorId(id);

		if (produto.getComercio() == null || !produto.getComercio().getId().equals(comercioId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Produto não pertence a este comércio");
		}

		produto.setNome(formData.getNome());
		produto.setDescricao(formData.getDescricao());
		produto.setCategoria(formData.getCategoria());
		produto.setUnidadeMedida(formData.getUnidadeMedida());

		Produto produtoSalvo = repository.save(produto);

		if (imagem != null && !imagem.isEmpty()) {
			Foto foto = fotoService.salvarFotoProduto(imagem, produtoSalvo);
			produtoSalvo.setFoto(foto);
			produtoSalvo = repository.save(produtoSalvo);
		}

		return produtoSalvo;
	}

	@Override
	public Foto buscarFotoPorId(Long id) {
		Produto produto = buscarPorId(id);
		return produto.getFoto();
	}

	@Override
	public List<ProdutoListaDTO> listarProdutosSemOfertaAtiva(Long comercioId) {
		 List<Produto> produtos = repository.buscarProdutosSemOfertaAtiva(comercioId);
		    
		 return produtos.stream()
		            .map(ProdutoListaDTO::fromEntity)
		            .toList();
	}

}
