package com.devf.hortilink.service;

import java.util.List;

import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;

public interface ProdutoService {

	List<Produto> listarTodos();
	Produto buscarPorId(Long id);
	Produto	excluirPorId(Long id);
	Produto salvar(Produto produto);
	Foto buscarFotosPorId(Long id);
	List<Produto> buscarCarrinho(List<Long> ids);
}
