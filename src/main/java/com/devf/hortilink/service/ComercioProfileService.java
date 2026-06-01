package com.devf.hortilink.service;

import java.util.List;

import com.devf.hortilink.dto.ComercioDTO;
import com.devf.hortilink.dto.CompletarPerfilComercioDTO;
import com.devf.hortilink.entity.ComercioProfile;
import com.devf.hortilink.entity.Oferta;


public interface ComercioProfileService {

	List<ComercioProfile> listarTodos();
	ComercioProfile buscarPorId(Long id);
	ComercioProfile excluirPorId(Long id);
	ComercioProfile completarPerfil(CompletarPerfilComercioDTO dto, Long idUsuario);
	List<ComercioDTO> listarPorCidade(String cidade);
	CompletarPerfilComercioDTO buscarPerfilComercioPorId(Long id);
	//ComercioProfile salvar(ComercioDTO comercioProfile, Usuario usuario);
	Oferta buscarOfertaPorId(Long idOferta);
	Oferta editarOferta(Long idOferta, Oferta oferta);
	Oferta adicionarOferta(Long id, Long idProduto, Oferta oferta);
	Oferta excluirOferta(Long idOferta);
}
