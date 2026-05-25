package com.devf.hortilink.service;

import java.util.List;

import com.devf.hortilink.dto.DetalheOfertaDTO;
import com.devf.hortilink.dto.OfertaDTO;
import com.devf.hortilink.dto.ProdutoCardDTO;
import com.devf.hortilink.entity.Oferta;

public interface OfertaService {

	List<Oferta> listarTodos();
	Oferta buscarPorId(Long id);
	Oferta excluirPorId(Long id);
	Oferta atualizar(Oferta oferta);
	List<OfertaDTO> buscarOfertasPorComercioId(Long id);
	List<ProdutoCardDTO> transformOfertas(List<Oferta> ofertas);
	List<OfertaDTO> listarOfertasParaApp();
	DetalheOfertaDTO buscarOfertaDetalhadaPorId(Long id);
	
}
