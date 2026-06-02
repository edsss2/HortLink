package com.devf.hortilink.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.dto.ProdutoListaDTO;
import com.devf.hortilink.entity.Foto;
import com.devf.hortilink.entity.Produto;

public interface ProdutoService {

	List<ProdutoListaDTO> listarProdutosPorComercio(Long comercioId);
	List<ProdutoListaDTO> listarProdutosSemOfertaAtiva(Long comercioId);
	void salvar(String emailUsuario, ProdutoFormDTO formData, MultipartFile imagem);
	Produto buscarPorId(Long id);
	Produto	excluirPorId(Long id);
	Foto buscarFotoPorId(Long id);
}
