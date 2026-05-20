package com.devf.hortilink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;
import com.devf.hortilink.repository.ProdutoRepository;
import com.devf.hortilink.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Override
	public List<Produto> listarTodos() {
		return repository.findAll();
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
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}

	@Override
	public Foto buscarFotosPorId(Long id) {
		Produto produto = buscarPorId(id);
		return produto.getFoto();
	}

	@Override
	public List<Produto> buscarCarrinho(List<Long> ids) {
		return repository.findAllById(ids);
	}
}
