package com.devf.hortilink.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.ProdutoCardDTO;
import com.devf.hortilink.dto.ProdutoFormDTO;
import com.devf.hortilink.entity.Oferta;

public interface OfertaService {

	List<Oferta> listarTodos();
	Oferta buscarPorId(Long id);
	Oferta excluirPorId(Long id);
	Oferta atualizar(Oferta oferta);
	List<Oferta> buscarCarrinho(List<Long> ids);
	List<ProdutoCardDTO> transformOfertas(List<Oferta> ofertas);
	void salvarNovoProduto(String emailUsuario, ProdutoFormDTO formData, MultipartFile imagemPrincipal, List<MultipartFile> imagensAdicionais);
	List<OfertaDTO> listarOfertasParaApp();
	
}
