package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.dto.ProdutoFormDTO;
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
	public List<Produto> listarProdutosPorComercio(String emailUsuario) {
		Usuario usuario = userService.buscarPorEmail(emailUsuario);
	    Long comercioId = usuario.getComercioProfile().getId();
	    
		return repository.findByComercioId(comercioId);
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
	public void salvar(String emailUsuario, ProdutoFormDTO formData, MultipartFile imagem) {
	    Usuario usuario = userService.buscarPorEmail(emailUsuario);
	    ComercioProfile comercioProfile = usuario.getComercioProfile();

	    Produto produto = new Produto();
	    produto.setNome(formData.getNome());
	    produto.setDescricao(formData.getDescricao());
	    produto.setCategoria(formData.getCategoria());
	    produto.setUnidadeMedida(formData.getUnidadeMedida());
	    produto.setComercio(comercioProfile);


	    Produto produtoSalvo = repository.save(produto);

	    Foto foto = fotoService.salvarFotoProduto(imagem, produtoSalvo); 
	    produto.setFoto(foto);
	    
	    repository.save(produto);

	}

	@Override
	public Foto buscarFotoPorId(Long id) {
		Produto produto = buscarPorId(id);
		return produto.getFoto();
	}

}
